package dev.khusanjon.finite_state_machine.BashScript;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import dev.khusanjon.finite_state_machine.BotFSM.Bot;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Scanner;

@ShellComponent
public class BotShell {
    Scanner scanner = new Scanner(System.in);
    String status = "Y";

    private final ActorSystem system = ActorSystem.create();

    private final ActorRef bot = system.actorOf(Bot.props(),"bot");

    @ShellMethod(value = "start bot ", key = "start")
    public void startBot() throws InterruptedException {
         do {
             bot.tell(new Bot.SearchBox(),ActorRef.noSender());
             bot.tell(new Bot.Printer(scanner.nextLine()),ActorRef.noSender());
             status = scanner.next();
             bot.tell(new Bot.Scanner(status),ActorRef.noSender());
             scanner.nextLine();
         } while (!status.equals("N"));
       }

}
