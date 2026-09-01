package com.example.legaltech;

import java.io.IOException;

public final class LifecycleMailService {
    private final InfraiClient client;

    public LifecycleMailService(InfraiClient client) { this.client = client; }

    public String deliver(Matter matter) throws IOException, InterruptedException {
        String subject;
        String html;
        switch (matter.stage()) {
            case INTAKE -> { subject = "Matter intake received: " + matter.reference(); html = "<p>We received your intake for " + matter.reference() + ".</p>"; }
            case SIGNED -> { subject = "Signed document ready: " + matter.reference(); html = "<p>Your signed document is ready in the secure matter workspace.</p>"; }
            case DEADLINE -> { subject = "Deadline follow-up: " + matter.reference(); html = "<p>Please review the upcoming deadline for this matter.</p>"; }
            default -> throw new IllegalArgumentException("Unsupported lifecycle stage");
        }
        return client.send(matter.recipient(), subject, html);
    }

    public record Matter(String reference, String recipient, Stage stage) {}
    public enum Stage { INTAKE, SIGNED, DEADLINE }
}
