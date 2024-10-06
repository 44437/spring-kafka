<h3>Notes:</h3>

- Watch out for importing json serializer
- TDD has died
- Synchronous producing has been used here
- You may be required to divorce consumer groups (for conduktor and spring)
---

<h3>Bugs:</h3>

- Sending strings butchered the message ffff but it works :p
 `Edit: It may be fixed through serializer interface but it is rather complicated, use a package (json, avro (the best) OR jackson, gson) for now` 
---

<h3>Tasks:</h3>

- [X] Take the variable offset etc. along with header.
- [X] If you have multiple partitions?
- [ ] If you have multiple brokers?
- [ ] Asynchronous producing, and handle errors by means of callback.
- [X] Read his some articles that seem useful. https://medium.com/@pravvich - `NAH non-satisfactory`
- [ ] delivery.timeout.ms in produce-config depends on the (a)synchronous producing, I gather. BUT the suggested thing is to regulate it as the time demanded to be request-timeout with infinite retries. 
- [ ] If you have multiple brokers?
- [ ] compression.type
- [ ] enable.idempotence, by default, is true, but if it is set false?
- [ ] I see a host of tests.
- [ ] cleanup.policy
- [ ] default commit and subscribe concepts have been used, play with them.
- [ ] auto.commit.interval.ms: disable it, manual commit is the key.
- [ ] client.dns.lookup
