package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.service.FlashcardService;
import pl.coderslab.service.PacketService;
import pl.coderslab.service.StudySessionService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

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


    @GetMapping("/flashpack/user/packets/{id}/study")
    public String startStudySession(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        studySessionService.startSession(user, packet);
        List<Flashcard> flashcards = flashcardService.getFlashcardsByPacketId(id);
        Collections.shuffle(flashcards); // Losowe mieszanie fiszek
        session.setAttribute("flashcards", flashcards);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("correctAnswers", 0);
        session.setAttribute("wrongAnswers", 0);
        model.addAttribute("packet", packet);
        model.addAttribute("flashcard", flashcards.get(0));
        model.addAttribute("correctAnswers", 0);
        model.addAttribute("wrongAnswers", 0);
        return "userStudy";
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
        } else {
            session.setAttribute("wrongAnswers", (int) session.getAttribute("wrongAnswers") + 1);
            flashcards.add(currentFlashcard); // jeśli niepoprawna, dodaj na koniec listy
        }

        currentIndex++;
        if (currentIndex >= flashcards.size()) {
            return "redirect:/flashpack/user/packets"; // wszystkie przerobione, koniec sesji
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
            case "additionalText":
                return flashcard.getAdditionalText().equalsIgnoreCase(answer);
            default:
                return false;
        }
    }

    @PostMapping("/flashpack/user/packets/{id}/study/end")
    public String endStudySession(@PathVariable Long id, HttpSession session) {
        session.removeAttribute("flashcards");
        session.removeAttribute("currentIndex");
        return "redirect:/flashpack/user/packets";
    }

}