package app;

import entity.Note;
import persistence.NoteRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final NoteRepository repository = new NoteRepository();

    public static void main(String[] args) {
        int option = 0;
        boolean loop = true;

        do {
            System.out.println("\n📓 === NOTES APP ===");
            System.out.println("1️⃣  Create note");
            System.out.println("2️⃣  List all notes");
            System.out.println("3️⃣  Edit a note");
            System.out.println("4️⃣  Delete a note");
            System.out.println("0️⃣  Exit");
            System.out.print("👉 Choose an option: ");

            try{
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("❌ Invalid input, please enter a number!");
                continue;
            }

            switch (option){
                case 1 -> createNote();
                case 2 -> listNotes();
                case 3 -> editNote();
                case 4 -> deleteNote();
                case 0 -> {
                    System.out.println("👋 Exiting... Goodbye!");
                    loop = false;
                }
                default -> System.out.println("⚠️  Invalid option, please try again!");
            }
        } while (loop);
    }

    private static void createNote() {
        System.out.println("\n📝 Create a new note");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Content: ");
        String content = scanner.nextLine();

        // Create note and set createdAt automatically
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now()); // ✅ automatically set current date & time

        repository.create(note);
        System.out.println("✅ Note created successfully!");
    }


    private static void listNotes() {
        System.out.println("\n📋 All notes:");
        List<Note> notes = repository.findAll();

        if (notes.isEmpty()) {
            System.out.println("⚠️  No notes found.");
            return;
        }

        // Define a nice formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        notes.forEach(note -> {
            System.out.println("\n---------------------");
            System.out.println("🆔 ID: " + note.getId());
            System.out.println("📖 Title: " + note.getTitle());
            System.out.println("✏️  Content: " + note.getContent());
            // Format the LocalDateTime nicely
            System.out.println("⏰ Created: " + note.getCreatedAt().format(formatter));
        });
    }


    private static void editNote() {
        System.out.print("\nEnter note ID to edit: ");
        int id = Integer.parseInt(scanner.nextLine());
        Note note = repository.findById(id);

        if (note == null){
            System.out.println("❌ Note not found!");
            return;
        }

        System.out.print("New title (" + note.getTitle() + "): ");
        String newTitle = scanner.nextLine();
        System.out.print("New content (" + note.getContent() + "): ");
        String newContent = scanner.nextLine();

        if (!newTitle.isBlank()) note.setTitle(newTitle);
        if (!newContent.isBlank()) note.setContent(newContent);

        repository.update(note);
        System.out.println("✅ Note updated successfully!");
    }

    private static void deleteNote() {
        System.out.print("\nEnter note ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        repository.delete(id);
        System.out.println("🗑️  Note deleted successfully!");
    }
}
