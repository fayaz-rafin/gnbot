import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Loader {

    String path;
    Gson gson;
    Loader(String path){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        gson = builder.create();
        this.path = path;
    }

    class User {
        public int stars;
        public int gnCount;
        public String name;

        User(String name, int gn, int stars){
            this.name = name;
            gnCount = gn;
            this.stars = stars;
        }
    }

    public int gn(String name, int gn){
        User user = getUser(name);
        if(user == null)
            user = new User(name, 0, 0);
        user.gnCount += gn;
        setUser(name, user);
        return user.gnCount;
    }

    public int stars(String name, int stars) {
        User user = getUser(name);
        if(user == null)
            user = new User(name, 0, 0);
        user.stars += stars;
        setUser(name, user);
        return user.stars;
    }

    private User getUser(String name){
        HashMap<String, User> data = gson.fromJson(read(), new TypeToken<HashMap<String, User>>(){}.getType());
        if(data == null)
            return null;

        return data.get(name);
    }

    private void setUser(String name,User user){
        HashMap<String, User> data = gson.fromJson(read(), new TypeToken<HashMap<String, User>>(){}.getType());
        if (data == null)
            data = new HashMap<String, User>();
        data.put(name, user);
        String json = gson.toJson(data);
        write(json);
    }

    private String read(){
        try{
            Path of = Path.of(path);
            if(!Files.exists(of))
                return null;

            return Files.readString(of);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(String data){
        try {
            Path of = Path.of(path);
            if(!Files.exists(of))
                Files.createFile(of);

            Files.write(of, data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
