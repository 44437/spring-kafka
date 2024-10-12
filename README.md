<h3>Notes:</h3>

- Watch out for importing json serializer
- TDD has died
- Synchronous producing has been used here
- You may be required to divorce/combine consumer groups (for conduktor and spring) `UI tools don't consume messages: The default variable of auto.offset.reset is 'latest'. When the app runs from scratch and CONSUMER GROUP doesn't know its offset, the offset will be determined as the offset of the latest message`
---

<h3>Bugs:</h3>

- Sending strings butchered the message ffff but it works :p
 `Edit: It may be fixed through serializer interface but it is rather complicated, use a package (json, avro (the best) OR jackson, gson) for now`
- <font color="green"><sup>*done*</sup></font> IsCompacted says NO. How, why? `It pertains to 'compact' in cleanup.policy, namely no bug here`
---

<h3>Tasks:</h3>

- [X] Take the variable offset etc. along with header.
- [X] If you have multiple partitions?
- [X] If you have multiple brokers?
- [X] Asynchronous producing, and handle errors by means of callback.
- [X] Read his some articles that seem useful. https://medium.com/@pravvich - `NAH non-satisfactory`
- [X] delivery.timeout.ms in produce-config depends on the (a)synchronous producing, I gather. BUT the suggested thing is to regulate it as the time demanded to be request-timeout with infinite retries. 
- [X] compression.type
- [X] enable.idempotence, by default, is true, but if it is set false?
- [X] I see a host of tests. `later on`
- [X] cleanup.policy
- [X] default commit and subscribe concepts have been used, play with them.
- [X] auto.commit.interval.ms: disable it, manual commit is the key.
- [X] client.dns.lookup
- [X] kafka connect `later on along with debezium`
- [ ] kafka streams
- [X] consumer groups
- [X] offset management
- [X] retention policy
- [X] exactly once
- [X] transactional consuming
- [X] leader election
- [X] reassignment
- [ ] schema registry
- [ ] ksqlDB
