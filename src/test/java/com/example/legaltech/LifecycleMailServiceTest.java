package com.example.legaltech;

import java.io.IOException;

public final class LifecycleMailServiceTest {
    public static void main(String[] args) throws Exception {
        var fake = new InfraiClient("http://127.0.0.1:1", "test") {
            @Override public String send(String to, String subject, String html) { return subject; }
        };
        var service = new LifecycleMailService(fake);
        String subject = service.deliver(new LifecycleMailService.Matter("MAT-7", "client@example.com", LifecycleMailService.Stage.DEADLINE));
        if (!subject.startsWith("Deadline follow-up: MAT-7")) throw new AssertionError(subject);
        System.out.println("lifecycle decision test passed");
    }
}
