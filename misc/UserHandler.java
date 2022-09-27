
public class UserHandler {
    Loader load;
    final static long DayLength = (long)8.64e7;
    UserHandler(String path){
        load = new Loader(path);
    }

    public class User {
        public int stars;
        public int gnCount;
        public int streak;
        public String id;
        public long lastGn;

        User(String id, int gn, int stars){
            this.id = id;
            gnCount = gn;
            this.stars = stars;
        }
    }


    public boolean gnReady(String id){
        User user = loadUser(id);
        if(System.currentTimeMillis() - DayLength >= user.lastGn)
            return true;

        return false;
    }
    public int gn(String id, int gn){
        User user = loadUser(id);
        user.gnCount += gn;

        if(System.currentTimeMillis() - 2*DayLength <= user.lastGn)
            user.streak++;
        else
            user.streak = 0;

        user.lastGn = System.currentTimeMillis();
        saveUser(id, user);
        return user.gnCount;
    }

    public int getStreak(String id){
        return loadUser(id).streak;
    }

    public int stars(String id, int stars) {
        User user = loadUser(id);
        user.stars += stars;
        saveUser(id, user);
        return user.stars;
    }

    private User loadUser(String id){
        User user = load.getUser(id);
        if(user == null)
            user = new User(id, 0, 0);
        return user;
    }

    private void saveUser(String id, User user){
        load.setUser(id, user);
    }
}
