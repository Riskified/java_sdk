## What and why

<!-- What does this PR change, and why? Link the Jira ticket. -->

Ticket:
Alignment plan step (if any): <!-- e.g. 2.3 — Customer -->

## Change type

<!-- Pick one. See API_ALIGNMENT_PLAN.md for the definitions. -->

- [ ] **Patch:** fixes the SDK's own behavior; no public API change and no change to what's sent
- [ ] **Additive:** new types, fields, getters/setters or enum values only; existing payloads unchanged
- [ ] **Bridge:** changes what's sent toward the documented form, keeping old code compiling
      (old members become `@Deprecated`, the compatibility switch restores the old format)
- [ ] **Other:** not part of the alignment plan (explain above)

## Ground rules

- [ ] No public type, field accessor, constructor or method is removed, renamed or has its signature
      changed (only additions and new `@Deprecated`)
- [ ] Validation (`validate(...)`, `Validate`, `Validation`) is untouched
- [ ] New fields use boxed types (`Integer`, `Double`, `Boolean`) and aren't added to existing constructors
- [ ] No new runtime dependencies; still builds for Java 8

## Golden diff

<!--
Paste the diff of src/test/resources/golden/ (git diff -- riskified-sdk/src/test/resources/golden),
or write "Empty". For patch and additive PRs it must be empty, apart from new sample files.
For bridge PRs, explain each change; the changelog lists it under "Wire changes".
-->

Empty

## Schema gaps

<!--
Did riskified-sdk/src/test/resources/schema/known-gaps.json change? List the entries removed
(gaps this PR fixes) or added (and why), or write "No change".
-->

No change
