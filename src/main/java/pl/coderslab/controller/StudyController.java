package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.app.NameShortenerUtil;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.model.StudySession;
import pl.coderslab.model.User;
import pl.coderslab.service.FlashcardService;
import pl.coderslab.service.PacketService;
import pl.coderslab.service.StudySessionService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StudyController {

    private final PacketService packetService;
    private final FlashcardService flashcardService;
    private final StudySessionService studySessionService;

    @Autowired
    public StudyController(PacketService packetService, FlashcardService flashcardService, StudySessionService studySessionService) {
        this.packetService = packetService;
        this.flashcardService = flashcardService;
        this.studySessionService = studySessionService;
    }

    // EKRAN WYBORU POWTÓRZEŃ PRZED NAUKĄ
    @GetMapping("/flashpack/user/packets/{id}/study")
    public String showStudySessionIntro(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        model.addAttribute("packetId", id);
        return "userStudyIntro";
    }

    @PostMapping("/flashpack/user/packets/{id}/study")
    public String startStudySession(@PathVariable Long id, @RequestParam int repetitions, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        StudySession studySession = studySessionService.startSession(user, packet);
        session.setAttribute("studySessionId", studySession.getId());
        List<Flashcard> flashcards = flashcardService.getFlashcardsByPacketId(id);

        // powtórz fiszki repetitions razy
        List<Flashcard> repeatFlashcards = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            repeatFlashcards.addAll(flashcards);
        }
        Collections.shuffle(repeatFlashcards); // losowe mieszanie fiszek

        session.setAttribute("flashcards", repeatFlashcards);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("correctAnswers", 0);
        session.setAttribute("wrongAnswers", 0);
        model.addAttribute("packet", packet);
        model.addAttribute("flashcard", repeatFlashcards.get(0));
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("wrongAnswers", 0);
        return "userStudy";
        //return "redirect:/flashpack/user/packets/" + id + "/study";
    }

//    @PostMapping("/flashpack/user/packets/{id}/study/answer")
//    public String checkAnswer(@PathVariable Long id, @RequestParam String answer, Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            throw new EntityNotFoundException("User not found");
//        }
//        List<Flashcard> flashcards = (List<Flashcard>) session.getAttribute("flashcards");
//        int currentIndex = (int) session.getAttribute("currentIndex");
//        Flashcard currentFlashcard = flashcards.get(currentIndex);
//
//        boolean isCorrect = isAnswerCorrect(currentFlashcard, answer, packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found")).getCompareField());
//        if (isCorrect) {
//            session.setAttribute("correctAnswers", (int) session.getAttribute("correctAnswers") + 1);
//            model.addAttribute("correctAnswer", null);
//        } else {
//            session.setAttribute("wrongAnswers", (int) session.getAttribute("wrongAnswers") + 1);
//            model.addAttribute("correctAnswer", getCorrectAnswer(currentFlashcard, packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found")).getCompareField()));
//            flashcards.add(currentFlashcard); // jeśli niepoprawna, dodaj na koniec listy
//        }
//
//
//        session.setAttribute("currentIndex", currentIndex);
//        model.addAttribute("packet", packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found")));
//        model.addAttribute("flashcard", flashcards.get(currentIndex));
//        model.addAttribute("correctAnswers", session.getAttribute("correctAnswers"));
//        model.addAttribute("wrongAnswers", session.getAttribute("wrongAnswers"));
//        System.out.println("+++++++++++++++++++++++++++++++++++" + session.getAttribute("correctAnswers") + "  " + session.getAttribute("wrongAnswers"));
//
////        currentIndex++;
////        if (currentIndex >= flashcards.size()) {
////            Long sessionId = (Long) session.getAttribute("studySessionId");
////            if (sessionId != null) {
////                studySessionService.endSession(sessionId);
////            }
////            return "redirect:/flashpack/user/packets"; // wszystkie przerobione, koniec sesji
////        }
////
//        currentIndex++;
//        if (currentIndex >= flashcards.size()) {
//            Long sessionId = (Long) session.getAttribute("studySessionId");
//            if (sessionId != null) {
//                endStudySession(id, session); // endStudySession z kontrolera, nie serwisu
//                studySessionService.endSession(sessionId);
//            }
//            return "redirect:/flashpack/user/packets"; // wszystkie przerobione, koniec sesji
//        }
//
//        return "userStudy";
//    }


    @PostMapping("/flashpack/user/packets/{id}/study/answer")
    public String checkAnswer(@PathVariable Long id, @RequestParam String answer, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        List<Flashcard> flashcards = (List<Flashcard>) session.getAttribute("flashcards");
        int currentIndex = (int) session.getAttribute("currentIndex");
        Flashcard currentFlashcard = flashcards.get(currentIndex);

        boolean isCorrect = isAnswerCorrect(currentFlashcard, answer, packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found")).getCompareField());
        if (isCorrect) {
            session.setAttribute("correctAnswers", (int) session.getAttribute("correctAnswers") + 1);
            model.addAttribute("correctAnswer", null);
        } else {
            session.setAttribute("wrongAnswers", (int) session.getAttribute("wrongAnswers") + 1);
            model.addAttribute("correctAnswer", getCorrectAnswer(currentFlashcard, packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found")).getCompareField()));
            flashcards.add(currentFlashcard); // jeśli niepoprawna, dodaj na koniec listy
        }

        // Zwiększ currentIndex przed aktualizacją atrybutów sesji
        currentIndex++;
        if (currentIndex >= flashcards.size()) {
            Long sessionId = (Long) session.getAttribute("studySessionId");
            if (sessionId != null) {
                endStudySession(id, session); // endStudySession z kontrolera, nie serwisu
            }
            return "redirect:/flashpack/user/packets"; // wszystkie przerobione, koniec sesji
        }

        session.setAttribute("currentIndex", currentIndex);
        model.addAttribute("packet", packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found")));
        model.addAttribute("flashcard", flashcards.get(currentIndex));
        model.addAttribute("correctAnswers", session.getAttribute("correctAnswers"));
        model.addAttribute("wrongAnswers", session.getAttribute("wrongAnswers"));
        System.out.println("+++++++++++++++++++++++++++++++++++" + session.getAttribute("correctAnswers") + "  " + session.getAttribute("wrongAnswers"));

        return "userStudy";
    }



    private boolean isAnswerCorrect(Flashcard flashcard, String answer, String compareField) {
        switch (compareField) {
            case "name":
                return flashcard.getName().equalsIgnoreCase(answer);
            case "word":
                return flashcard.getWord().equalsIgnoreCase(answer);
            case "word2":
                return flashcard.getWord2().equalsIgnoreCase(answer);
            case "additionalText":
                return flashcard.getAdditionalText().equalsIgnoreCase(answer);
            default:
                return false;
        }
    }

    private String getCorrectAnswer(Flashcard flashcard, String compareField) {
        switch (compareField) {
            case "name":
                return flashcard.getName();
            case "word":
                return flashcard.getWord();
            case "word2":
                return flashcard.getWord2();
            case "additionalText":
                return flashcard.getAdditionalText();
            default:
                return "";
        }
    }

    @PostMapping("/flashpack/user/packets/{id}/study/end")
    public String endStudySession(@PathVariable Long id, HttpSession sess) {

        Long sessionId = (Long) sess.getAttribute("studySessionId"); // Pobierz id sesji z sesji
        if (sessionId != null) {
            StudySession sessionToEnd = studySessionService.findSessionById(sessionId);
            // sprawdza, czy sesja jest powiązana z pakietem
            if (sessionToEnd != null && sessionToEnd.getPacket().getId().equals(id)) {
                sessionToEnd.setCorrectAnswers((int) sess.getAttribute("correctAnswers"));
                sessionToEnd.setWrongAnswers((int) sess.getAttribute("wrongAnswers"));

                System.out.println("||||||||||||||||||||||| corr.ans. " + sessionToEnd.getCorrectAnswers());
                System.out.println("||||||||||||||||||||||| wro.ans. " + sessionToEnd.getWrongAnswers());

                studySessionService.endSession(sessionToEnd);

            } else {
                throw new EntityNotFoundException("No session associated with this packet found.");
            }
        }
        sess.removeAttribute("flashcards");
        sess.removeAttribute("currentIndex");
        return "redirect:/flashpack/user/packets";
    }

    @PostMapping("/user/home")
    public String getStats(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        List<StudySession> studySessions = studySessionService.getSessionsPerPacket(user.getId());

        // pobiera ostatnią sesję dla danego usera do guzika Last studied
        StudySession lastSession = null;
        if (!studySessions.isEmpty()) {
            lastSession = studySessions.get(studySessions.size() - 1);
        }
        // walidacja sesji - jeśli są z pustymi polami, to je pomija
        List<StudySession> validSessions = studySessions.stream()
                .filter(s -> s.getPacket() != null
                        && s.getStartTime() != null
                        && s.getEndTime() != null
                        && s.getDuration() > 0)
                .collect(Collectors.toList());

        // sesje z pustymi polami usuwa nawet z bazy
        studySessions.stream()
                .filter(s -> s.getPacket() == null
                        || s.getStartTime() == null
                        || s.getEndTime() == null
                        || s.getDuration() <= 0)
                .forEach(s -> studySessionService.deleteStudySession(s.getId()));

        // jeśli nie ma sesji, tzn. validSessions jest puste
        if (validSessions.isEmpty()) {
            model.addAttribute("sortedPackets", Collections.emptyList());
            model.addAttribute("totalDuration", 0);
            return "userPage";
        }

        // grupuje sesje wg pakietu, sumuje czas trwania
        Map<Packet, Long> durationMap = validSessions.stream()
                .collect(Collectors.groupingBy(StudySession::getPacket,
                        Collectors.summingLong(StudySession::getDuration)));

        // sort. pakietów wg ogólnego czasu trwania
        List<Packet> sortedPackets = durationMap.keySet().stream()
                .sorted(Comparator.comparingLong(durationMap::get).reversed())
                .limit(10)
                .collect(Collectors.toList());

        // całkowity czas nauki
        long totalDuration = durationMap.values().stream().mapToLong(Long::longValue).sum();

        // wybiera ostatnie 3 sesje
        List<StudySession> recentSessions = validSessions.stream()
                .sorted(Comparator.comparing(StudySession::getEndTime).reversed())
                .limit(3)
                .collect(Collectors.toList());
        // wybiera ostatnią jedną sesję
        List<StudySession> lastOneSession = validSessions.stream()
                .sorted(Comparator.comparing(StudySession::getEndTime).reversed())
                .limit(1)
                .collect(Collectors.toList());

        // średnia z poprawnych odp.
        // total
        double avgCorrectTotalAnswers = validSessions.stream()
                .mapToInt(StudySession::getCorrectAnswers)
                .average()
                .orElse(0);
        // ostatnie 3
        double avgCorrectRecentAnswers = recentSessions.stream()
                .mapToInt(StudySession::getCorrectAnswers)
                .average()
                .orElse(0);
        // ostatnia jedna
        double avgCorrectLastOneAnswers = lastOneSession.stream()
                .mapToInt(StudySession::getCorrectAnswers)
                .average()
                .orElse(0);

        // średnia z błędnych odpowiedzi
        // total
        double avgWrongTotalAnswers = validSessions.stream()
                .mapToInt(StudySession::getWrongAnswers)
                .average()
                .orElse(0);
        // ostatnie 3
        double avgWrongRecentAnswers = recentSessions.stream()
                .mapToInt(StudySession::getWrongAnswers)
                .average()
                .orElse(0);
        // ostatnia jedna
        double avgWrongLastOneAnswers = lastOneSession.stream()
                .mapToInt(StudySession::getWrongAnswers)
                .average()
                .orElse(0);

        session.setAttribute("lastSession", lastSession.getPacket());
        model.addAttribute("lastSessionName", NameShortenerUtil.shortenName(lastSession.getPacket().getName(), 1, 12));
        model.addAttribute("lastSessionPacketId", lastSession.getPacket().getId());
        model.addAttribute("sortedPackets", sortedPackets);
        model.addAttribute("durationMap", durationMap);
        model.addAttribute("totalDuration", totalDuration);
        model.addAttribute("avgCorrectTotalAnswers", avgCorrectTotalAnswers);
        model.addAttribute("avgCorrectRecentAnswers", avgCorrectRecentAnswers);
        model.addAttribute("avgCorrectLastOneAnswers", avgCorrectLastOneAnswers);
        model.addAttribute("avgWrongTotalAnswers", avgWrongTotalAnswers);
        model.addAttribute("avgWrongRecentAnswers", avgWrongRecentAnswers);
        model.addAttribute("avgWrongLastOneAnswers", avgWrongLastOneAnswers);

        return "userPage";
    }


    }
