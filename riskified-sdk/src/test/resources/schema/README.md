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

Then update the table above and run
`mvn test -Dtest=SchemaConformanceTest -Dschema.gaps.update=true` to refresh `known-gaps.json`.
Review that diff: every entry should be a gap the alignment plan tracks.

## `known-gaps.json`

The violations each check is expected to report today. The test fails on a violation that isn't
listed, and on a listed one that no longer happens, so remove entries as the plan's steps fix them.

## `accepted-differences.json`

Places where the SDK deliberately differs from the docs, each with the doc, the exact violation and
the reason (usually a docs inconsistency). The test leaves these out before comparing, so
`known-gaps.json` only lists real SDK gaps. Add an entry only for a decided difference, not for a
gap still to fix.
