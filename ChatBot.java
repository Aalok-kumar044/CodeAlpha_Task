import java.util.*;

public class ChatBot {
    private static Map<String, String> responses = new HashMap<>();

    // Initialize chatbot responses
    static {
        responses.put("hello", "Hello! How can I help you today?");
        responses.put("hi", "Hi there! What can I do for you?");
        responses.put("how are you", "I'm just a program, but I'm doing great! Thanks for asking.");
        responses.put("bye", "Goodbye! Have a nice day.");
        responses.put("thanks", "You're welcome!");
        responses.put("help", "I can answer basic questions. Try asking about 'Java', 'internship', or say 'bye'.");
        responses.put("java", "Java is a versatile programming language used for building applications.");
        responses.put("internship", "Internships help you gain practical experience and build your resume.");
    }

    // Method to get response
    public static String getResponse(String input) {
        input = input.toLowerCase().trim();

        // Check if input matches any keyword
        for (String key : responses.keySet()) {
            if (input.contains(key)) {
                return responses.get(key);
            }
        }
        return "I'm sorry, I don't understand that. Can you rephrase?";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== AI ChatBot ===");
        System.out.println("Type 'bye' to exit.");

        while (true) {
            System.out.print("You: ");
            String userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Bot: " + responses.get("bye"));
                break;
            }

            String reply = getResponse(userInput);
            System.out.println("Bot: " + reply);
        }

        sc.close();
    }
}

