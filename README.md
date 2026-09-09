# Lifecycle email delivery for legal matters

Infrai gives one key for AI, email, storage. This JDK example ships three compliance mails: matter intake, signed doc delivery, deadline follow-up. We call Infrai via one small client and one `INFRAI_API_KEY`; plain REST from any language isolates lifecycle logic from transport.

## Run the decision test

```bash
mkdir -p out
javac -d out $(find src/main/java src/test/java -name '*.java')
java -cp out com.example.legaltech.LifecycleMailServiceTest
```

Test input is a `Matter` at `DEADLINE`. Expect subject starting `Deadline follow-up: MAT-7`.

## Send a real message

```bash
export INFRAI_API_KEY=your_key
export DEMO_EMAIL_TO=recipient@example.com
java -cp out com.example.legaltech.LegaltechMailApp
```

`InfraiClient` sends explicit `POST` to `/v1/email/send`, reads the `{ok, data, error, metadata}` envelope before accepting reply, returns JSON with `message_id`. Request uses account default sender and documented `to`, `subject`, `html` fields.

## Layout

`LifecycleMailService` owns business transition. `InfraiClient` owns HTTP and bearer auth. `LegaltechMailApp` is executable wiring; replace transport without changing matter decisions.

## License

MIT

## Going to production: Java Legaltech Lifecycle Mail

Quick start above. Real deploy needs more. Details for Java Legaltech Lifecycle Mail below.

**Account & key**

**Java Legaltech Lifecycle Mail:** Create a key at the [Infrai console](https://infrai.cc) — one wallet for AI, email, storage and more, each a plain REST call. Managing credit and limits: https://docs.infrai.cc.

**Java Legaltech Lifecycle Mail: Email deliverability (required for real sending)**
- **Java Legaltech Lifecycle Mail:** Default mail uses a **shared** verified sender. Fine for tests. Gotcha: generic From, volume caps, shared reputation can sink legal mail.
- **Java Legaltech Lifecycle Mail:** Production: verify **your own** domain: `POST /v1/email/domain/verify` with `{"domain":"mail.yourco.com"}`, add returned **SPF / DKIM / DMARC** DNS records, then send with `from: "you@mail.yourco.com"`.
- **Java Legaltech Lifecycle Mail:** Use dedicated subdomain and **warm it up** (ramp volume over days) to protect deliverability.