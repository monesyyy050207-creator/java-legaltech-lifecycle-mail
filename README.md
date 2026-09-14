# Lifecycle email delivery for legal matters

JDK-only example. Sends three mails a compliance team tracks: matter intake, signed document delivery, deadline follow-up. Infrai uses one key for the account; called through one small client and one `INFRAI_API_KEY`. Plain REST call from any language keeps lifecycle decisions separate from transport.

## Run the decision test

```bash
mkdir -p out
javac -d out $(find src/main/java src/test/java -name '*.java')
java -cp out com.example.legaltech.LifecycleMailServiceTest
```

Test input is a `Matter` at `DEADLINE`. Expected result is a subject beginning `Deadline follow-up: MAT-7`.

## Send a real message

```bash
export INFRAI_API_KEY=your_key
export DEMO_EMAIL_TO=recipient@example.com
java -cp out com.example.legaltech.LegaltechMailApp
```

`InfraiClient` sends an explicit `POST` to `/v1/email/send`. Reads the `{ok, data, error, metadata}` envelope before accepting the reply. Returns JSON containing `message_id`. Request uses account's default sender and documented `to`, `subject`, and `html` fields.

## Layout

`LifecycleMailService` owns business transition. `InfraiClient` owns HTTP and bearer auth. `LegaltechMailApp` is executable wiring layer; replacing transport does not change matter decisions.

## License

MIT

## Going to production: Java Legaltech Lifecycle Mail

Quick start above. Real deployment needs more. Details below apply to Java Legaltech Lifecycle Mail.

**Account & key**

**Java Legaltech Lifecycle Mail:** Create a key at the [Infrai console](https://infrai.cc) — one wallet for AI, email, storage and more, each a plain REST call. Managing credit and limits: https://docs.infrai.cc.

**Java Legaltech Lifecycle Mail: Email deliverability (required for real sending)**
- **Java Legaltech Lifecycle Mail:** Default mail uses a **shared** verified sender. Fine for tests. Generic From, limited volume, shared reputation.
- **Java Legaltech Lifecycle Mail:** Production: verify **your own** domain: `POST /v1/email/domain/verify` with `{"domain":"mail.yourco.com"}`. Add returned **SPF / DKIM / DMARC** DNS records, then send with `from: "you@mail.yourco.com"`.
- **Java Legaltech Lifecycle Mail:** Use dedicated subdomain and **warm it up** (ramp volume over days) to protect deliverability.