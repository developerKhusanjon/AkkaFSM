package dev.khusanjon.finite_state_machine.BotFSM;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.springframework.web.reactive.function.client.WebClient;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;


public class Bot extends AbstractLoggingActor {
    WebClient webClient = WebClient.create();


    public static class Printer {
        private final String question;

        public Printer(String question) {
            this.question = question;
        }
    }

    public static class Scanner {
        private final String cmd;

        public Scanner(String cmd) {
            this.cmd = cmd;
        }
    }

    public static class SearchBox {
    }

    private final PartialFunction<Object, BoxedUnit> printer;
    private final PartialFunction<Object, BoxedUnit> scanner;
    private final PartialFunction<Object, BoxedUnit> searchbox;

    public Bot() {

        printer = ReceiveBuilder
                .match(Printer.class, this::onPrinter)
                .build();

        scanner = ReceiveBuilder
                .match(Scanner.class, this::onScanner)
                .build();

        searchbox = ReceiveBuilder
                .match(SearchBox.class, this::onSearchBox)
                .build();

        receive(searchbox);
    }

    private void onPrinter(Printer printer) {
        System.out.println("Question accepted, searching knowledge base...");
        System.out.println(webClient
                .get()
                .uri("http://localhost:8080/ask?question=" + printer.question)
                .retrieve()
                .bodyToMono(String.class)
                .block());
        System.out.println("Do you want to continue...(Y/N)");
        context().become(scanner);
    }

    private void onScanner(Scanner scanner) {
        if (scanner.cmd.equals("Y")||scanner.cmd.equals("N"))
            context().become(searchbox);

    }

    private void onSearchBox(SearchBox searchBox) {
        System.out.println("Please, ask your question: ");
        context().become(printer);
    }

    public static Props props() {
        return Props.create(Bot.class);
    }

}
