package dev.khusanjon.finite_state_machine.BotFSM;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import akka.testkit.TestProbe;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.spy;


class BotTest {
    private final ActorSystem actorSystem = spy(ActorSystem.create());


    @Test
    public void testActor() {
        new TestKit(actorSystem) {
            {
                final TestProbe probe = TestProbe.apply(actorSystem);
                final ActorRef BOT = actorSystem.actorOf(Bot.props());
                BOT.tell(new Bot.Printer("Is that working...?"), actorSystem.actorOf(Bot.props()));
                probe.expectNoMsg();
                BOT.tell(new Bot.Scanner("Y"), actorSystem.actorOf(Bot.props()));
                probe.expectNoMsg();
                BOT.tell(new Bot.SearchBox(), actorSystem.actorOf(Bot.props()));
                probe.expectNoMsg();
            }
        };
    }
}