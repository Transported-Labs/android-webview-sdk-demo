# android-webview-sdk-demo

Write all documentation and all files in English.

Android demo app (Kotlin, Gradle, single `:app` module) for the CUELive Lightshow WebView SDK (`com.github.Transported-Labs:android-webview-sdk`, pulled from JitPack). Sources live in `app/src/main` (`MainActivity.kt`, `assets/index.html`); unit tests in `app/src/test`, instrumented tests in `app/src/androidTest`.

## Repo etiquette

- Branch off `main` (the main branch); merge feature branches back into it.
- Conventional-commit subjects (`feat:`, `fix:`, `refactor:`).
- Commit or push only when asked.

## Core Principle: KISS

Keep it simple. Always reach for the smallest solution that solves the problem in front of you.

- No speculative abstraction, no premature generalization, no feature you weren't asked for (YAGNI).
- When code repeats, prefer duplication over an abstraction that adds coupling — wait until a pattern is proven before extracting it.
- Fewer moving parts beats clever. Optimize for the next person reading the code, not for the fewest lines.
- Match the surrounding code's idioms, naming, and structure rather than introducing a new style.

## Code Comments

Code explains itself through naming and structure. Default to **no comments**. Add one only when WHY is genuinely non-obvious (a hidden constraint, a bug workaround, a subtle invariant) — never WHAT. Keep it to **one short line**; no multi-paragraph or docstring-style blocks. Applies to every language.

## Testing

- Every code change carries tests for the new and modified behavior — success and error paths.
- Test behavior, not implementation details.
- Prefer real dependencies over mocks so tests exercise the actual integration.
- Local unit tests use JUnit 4 (`app/src/test`, `./gradlew test`); device tests use AndroidX Test + Espresso (`app/src/androidTest`, `./gradlew connectedAndroidTest`).

## Linters & Cleanup

Always fix **every** problem you discover in the repo — lint warnings, failing tests, red CI, stale docs — even pre-existing and unrelated. Land it as a focused sibling change in the same session.

Always run **Android lint** (`./gradlew lint`), **unit tests** (`./gradlew test`) and **build** (`./gradlew assemble`, the same command CI runs) after any change, and fix every issue they report before considering the work done.

## CI / Azure Pipelines

After every push to `main`, watch the triggered build to completion and report the result — never push and walk away.

The pipeline is `azure-pipelines.yml`, registered in Azure DevOps as `Transported-Labs.android-webview-sdk-demo` (org `transported-labsW4VWWG`, project `Transported Labs 1`, `definitionId: 78`). It triggers on `main`, builds the APKs with `./gradlew assemble`, and deploys them to the dev DXP CDN (`webview-sdk/android`). Monitoring:

- **Find the build via the raw REST builds API, NOT `az pipelines build list`.** `az pipelines build list` omits freshly-queued/`inProgress` builds — right after a push it returns only older `completed` builds, so the new run looks missing. The REST API surfaces it immediately. Use `queryOrder=queueTimeDescending` and take `value[0]`:
  `az rest --method get --resource 499b84ac-1321-427f-aa17-267ca6975798 --uri "https://dev.azure.com/transported-labsW4VWWG/Transported%20Labs%201/_apis/build/builds?definitions=78&\$top=5&queryOrder=queueTimeDescending&api-version=7.1" --query "value[].{id:id,status:status,result:result,src:sourceVersion}"`
  Match the build to your push by `sourceVersion` (the commit SHA) — don't assume the top one is yours. `buildNumber` is Azure's timezone-based date counter, distinct from `Build.BuildId`; never key off it.
- **Poll the timeline** for stage/step state (the top-level `az pipelines build show` only gives overall status). It needs the Azure DevOps AAD resource id and the project in the path:
  `az rest --method get --resource 499b84ac-1321-427f-aa17-267ca6975798 --uri "https://dev.azure.com/transported-labsW4VWWG/Transported%20Labs%201/_apis/build/builds/<id>/timeline?api-version=7.1"`
  Filter `records[]` by `type` (`Stage`/`Job`/`Task`) and read `state` + `result`.
