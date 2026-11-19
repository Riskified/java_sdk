# API schemas for `SchemaConformanceTest`

Each `*-flow-*.json` file is the OpenAPI 3.0 definition embedded in one page of the Chargeback
Guarantee API reference (developers.riskified.com). They are copied as-is.

| File | Endpoint | Page `updatedAt` |
|---|---|---|
| `pre-auth-flow-decide.json` | pre-auth `/decide` | 2026-06-04T08:05:19Z |
| `pre-auth-flow-checkout_denied.json` | pre-auth `/checkout_denied` | 2026-06-04T08:05:19Z |
| `pre-auth-flow-decision.json` | pre-auth `/decision` | 2026-06-04T08:05:19Z |
| `post-auth-flow-submit.json` | post-auth `/submit` | 2026-06-04T08:08:08Z |
| `post-auth-flow-decide.json` | post-auth `/decide` | 2026-06-04T08:08:08Z |
| `post-auth-flow-checkout_denied.json` | post-auth `/checkout_denied` | 2026-06-04T08:08:08Z |

These are a Java-local copy until a shared cross-SDK schema exists.

## Refreshing

Save each page's Markdown export (the pages are listed in
`https://developers.riskified.com/llms.txt`), then take the first fenced `json` block as the
definition:

```sh
python3 - <<'EOF'
import json, re, sys, glob, os
for page in glob.glob("path/to/api_docs/*-flow-*.md"):
    text = open(page).read()
    spec = json.loads(re.search(r"```json\n(.*?)\n```", text, re.S).group(1))
    name = os.path.basename(page)[:-3] + ".json"
    with open(name, "w") as out:
        json.dump(spec, out, indent=2, ensure_ascii=False)
        out.write("\n")
EOF
```

Then update the table above and run `mvn test -Dtest=SchemaConformanceTest`. A refresh can surface
new violations: fix the SDK, or, if the difference is decided, add it to `accepted-differences.json`.

## `accepted-differences.json`

Places where the SDK deliberately differs from the docs, each with the doc, the exact violation and
the reason (usually a docs inconsistency, or a value the server accepts anyway). The test fails on
any other violation. Add an entry only for a decided difference, never to silence a gap still to
fix.

`"doc": "*"` matches every doc, and a message starting with `*` matches any message ending with the
rest, so `*/date_of_birth: <violation>` covers `/order/customer/date_of_birth` and
`/checkout/passengers/0/date_of_birth`. Keep the full violation text after the path so a wildcard
can't hide a different kind of problem.
