package xyz.dreeks.audhumla.auth;

public class YggdrasilAgent {

    public String name;
    public int version;

    public YggdrasilAgent() {}

    public YggdrasilAgent(String agent, int version) {
        this.name = agent;
        this.version = version;
    }

    public static YggdrasilAgent getMinecraft() {
        return new YggdrasilAgent("Minecraft", 1);
    }

}
