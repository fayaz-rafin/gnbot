import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
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

    public UserHandler.User getUser(String name){
        HashMap<String, UserHandler.User> data = gson.fromJson(read(), new TypeToken<HashMap<String, UserHandler.User>>(){}.getType());
        if(data == null)
            return null;

        return data.get(name);
    }

    public void setUser(String name, UserHandler.User user){
        HashMap<String, UserHandler.User> data = gson.fromJson(read(), new TypeToken<HashMap<String, UserHandler.User>>(){}.getType());
        if (data == null)
            data = new HashMap<String, UserHandler.User>();
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
