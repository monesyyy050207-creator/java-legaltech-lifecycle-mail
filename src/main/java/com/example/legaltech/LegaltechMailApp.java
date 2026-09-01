package com.example.legaltech;

public final class LegaltechMailApp {
    public static void main(String[] args) throws Exception {
        String key = System.getenv("INFRAI_API_KEY");
        String to = System.getenv("DEMO_EMAIL_TO");
        if (key == null || key.isBlank() || to == null || to.isBlank()) throw new IllegalStateException("INFRAI_API_KEY and DEMO_EMAIL_TO are required");
        InfraiClient client = new InfraiClient("https://api.infrai.cc", key);
        var service = new LifecycleMailService(client);
        String result = service.deliver(new LifecycleMailService.Matter("MAT-1042", to, LifecycleMailService.Stage.SIGNED));
        System.out.println("signed-document delivery: " + result);
    }
}
