import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.awt.*;

public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;
    private static JDA jda;
    private static UserHandler users;
    public static void main(String[] args) {
        Dotenv env = Dotenv.load();// Environment variable holder
        users = new UserHandler("UserData.json");

        System.out.println(env.get("TOKEN"));
        String TOKEN= env.get("TOKEN");
        jdaBuilder = JDABuilder.createDefault(TOKEN);// string toke

        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.watching("Playing /gn"));
        jdaBuilder.addEventListeners(new Main());


        try {
            jda = jdaBuilder.build();

        } catch (LoginException exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction action = jda.updateCommands();

        action.addCommands(new CommandData("gn", "Say Gn")).complete();


    }


    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        String name = event.getName();



        if(name.equals("gn")){
            
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.white);
            embedBuilder.setTitle("Thanks for your Good Nights");
            String id = event.getMember().getId();
            if(users.gnReady(id)){
                embedBuilder.setDescription("**Thanks, your gn count is:** " + users.gn(id, 1) + " **You have collected:** " + users.stars(id, 1) + " **stars** "  );
                event.replyEmbeds(embedBuilder.build()).addActionRow(Button.primary("view", "See my stats"), Button.danger("table", "See LeaderBoard")).queue();
            } else {
                embedBuilder.setDescription("Once per day only :( Try again tomorrow!");
                event.replyEmbeds(embedBuilder.build()).addActionRow(Button.primary("view", "See my stats"), Button.danger("table", "See LeaderBoard")).queue();
            }
        }
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {

    }
}
