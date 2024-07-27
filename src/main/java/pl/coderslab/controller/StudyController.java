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
        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        model.addAttribute("packetId", id);
        model.addAttribute("flashcardsNo", packet.getFlashcards().size());

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
        model.addAttribute("studySession", studySession);
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

        currentIndex++;
        if (currentIndex >= flashcards.size()) {
            Long sessionId = (Long) session.getAttribute("studySessionId");
            if (sessionId != null) {
                endStudySession(id, session, model); // endStudySession z kontrolera, nie serwisu
            }
//            return "redirect:/flashpack/user/packets"; // wszystkie przerobione, koniec sesji//////OUTRO!!!
            return "userStudyOutro";
        }

        session.setAttribute("currentIndex", currentIndex);
        model.addAttribute("packet", packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found")));
        model.addAttribute("flashcard", flashcards.get(currentIndex));
        model.addAttribute("correctAnswers", session.getAttribute("correctAnswers"));
        model.addAttribute("wrongAnswers", session.getAttribute("wrongAnswers"));
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
    public String endStudySession(@PathVariable Long id, HttpSession sess, Model model) {
        Long sessionId = (Long) sess.getAttribute("studySessionId"); // Pobierz id sesji z sesji
        if (sessionId != null) {
            StudySession sessionToEnd = studySessionService.findSessionById(sessionId);
            // sprawdza, czy sesja jest powiązana z pakietem
            if (sessionToEnd != null && sessionToEnd.getPacket().getId().equals(id)) {
                sessionToEnd.setCorrectAnswers((int) sess.getAttribute("correctAnswers"));
                sessionToEnd.setWrongAnswers((int) sess.getAttribute("wrongAnswers"));

                StudySession endedSession = studySessionService.endSession(sessionToEnd); // pobiera duration i zapis sesji w StSessServ

                model.addAttribute("correctAnswers", endedSession.getCorrectAnswers());
                model.addAttribute("wrongAnswers", endedSession.getWrongAnswers());
                model.addAttribute("duration", endedSession.getDuration());

            } else {
                throw new EntityNotFoundException("No session associated with this packet found.");
            }
        }
        sess.removeAttribute("flashcards");
        sess.removeAttribute("currentIndex");
        sess.removeAttribute("studySessionId");
        System.out.println("]]]]]]]]]]]]]]]]]]]]] koniec endStSess");
        return "userStudyOutro";
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
            if (lastSession != null && lastSession.getPacket().getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
                lastSession = null;
            }
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

        // jeśli nie ma sesji nauki, tzn. validSessions jest puste
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

        // NOWE PODEJŚCIE

        Map<Packet, Integer> correctAnswerPercentagesTotal = new HashMap<>();
        Map<Packet, Integer> wrongAnswerPercentagesTotal = new HashMap<>();
        Map<Packet, Integer> correctAnswerPercentagesRecent = new HashMap<>();
        Map<Packet, Integer> wrongAnswerPercentagesRecent = new HashMap<>();
        Map<Packet, Integer> correctAnswerPercentagesLastOne = new HashMap<>();
        Map<Packet, Integer> wrongAnswerPercentagesLastOne = new HashMap<>();

        for (StudySession s : validSessions) {
            Packet packet = s.getPacket();
            int totalAnswers = s.getCorrectAnswers() + s.getWrongAnswers();
            if (totalAnswers > 0) {
                int correctPercentage = (int) Math.round((double) s.getCorrectAnswers() / totalAnswers * 100);
                int wrongPercentage = 100 - correctPercentage;

                correctAnswerPercentagesTotal.merge(packet, correctPercentage, (oldValue, newValue) -> (oldValue + newValue) / 2);
                wrongAnswerPercentagesTotal.merge(packet, wrongPercentage, (oldValue, newValue) -> (oldValue + newValue) / 2);
            }
        }

        for (StudySession s : recentSessions) {
            Packet packet = s.getPacket();
            int totalAnswers = s.getCorrectAnswers() + s.getWrongAnswers();
            if (totalAnswers > 0) {
                int correctPercentage = (int) Math.round((double) s.getCorrectAnswers() / totalAnswers * 100);
                int wrongPercentage = 100 - correctPercentage;

                correctAnswerPercentagesRecent.merge(packet, correctPercentage, (oldValue, newValue) -> (oldValue + newValue) / 2);
                wrongAnswerPercentagesRecent.merge(packet, wrongPercentage, (oldValue, newValue) -> (oldValue + newValue) / 2);
            }
        }

        for (Packet packet : sortedPackets) {
            List<StudySession> lastOneSessionForPacket = validSessions.stream()
                    .filter(s -> s.getPacket().equals(packet))
                    .sorted(Comparator.comparing(StudySession::getEndTime).reversed())
                    .limit(1)
                    .collect(Collectors.toList());

            for (StudySession s : lastOneSessionForPacket) {
                int totalAnswers = s.getCorrectAnswers() + s.getWrongAnswers();
                if (totalAnswers > 0) {
                    int correctPercentage = (int) Math.round((double) s.getCorrectAnswers() / totalAnswers * 100);
                    int wrongPercentage = 100 - correctPercentage;

                    correctAnswerPercentagesLastOne.put(packet, correctPercentage);
                    wrongAnswerPercentagesLastOne.put(packet, wrongPercentage);
                }
            }
        }

        model.addAttribute("correctAnswerPercentagesTotal", correctAnswerPercentagesTotal);
        model.addAttribute("wrongAnswerPercentagesTotal", wrongAnswerPercentagesTotal);
        model.addAttribute("correctAnswerPercentagesRecent", correctAnswerPercentagesRecent);
        model.addAttribute("wrongAnswerPercentagesRecent", wrongAnswerPercentagesRecent);
        model.addAttribute("correctAnswerPercentagesLastOne", correctAnswerPercentagesLastOne);
        model.addAttribute("wrongAnswerPercentagesLastOne", wrongAnswerPercentagesLastOne);
        if (lastSession != null) {
            model.addAttribute("lastSessionName",
                    NameShortenerUtil.shortenName(lastSession.getPacket().getName(), 1, 16));
            session.setAttribute("lastSession", lastSession.getPacket());
            model.addAttribute("lastSessionPacketId", lastSession.getPacket().getId());
        }
        model.addAttribute("sortedPackets", sortedPackets);
        model.addAttribute("durationMap", durationMap);
        model.addAttribute("totalDuration", totalDuration);

        return "userPage";
    }
}
