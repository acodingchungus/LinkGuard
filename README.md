# LinkGuard

A small Java console app that helps people **notice** possible warning signs in a URL, without ever opening it.

Built solo at PlutoHacks 2026 (Broward College) for the theme "Build, Protect, Innovate." This is a new project created during the hackathon.

## The problem

Phishing links often use tricks that are hard to spot at a glance. LinkGuard reads a link's text and points out a few common warning signs so people can slow down and think before clicking.

**LinkGuard does not decide whether a site is safe.** It only checks a few signs in the text.

## What it checks

| Check | Why it matters |
|---|---|
| Uses `http://` instead of `https://` | Information sent to the site is not encrypted. |
| `@` before the host | Browsers ignore everything before the `@`, so `http://bank.example@192.0.2.10` actually goes to `192.0.2.10`. An `@` later in the path (like `/@profile`) is not flagged. |
| Numeric IP address as the host | Real sites usually use names. A raw IP hides who you're connecting to. |

**Caution level:** 0 warnings = LOW, 1 = MEDIUM, 2 or more = HIGH. This is a simple rule I chose, not a scientific score.

## How to run

**Requirements:** Java 17 or newer (built and tested with Java 25 in Eclipse).

**In Eclipse:** open `LinkGuard.java`, click Run, then click inside the Console to type a URL.

**From Terminal:**
```bash
cd ~/eclipse-workspace/plutohacks/src
javac plutohacks/LinkGuard.java
java plutohacks.LinkGuard
```

## Example
...
=== LinkGuard ===
Looks at a URL's text for warning signs. It never opens the link.
Enter a URL: https://exmaple.test
You entered: https://exmaple.test

Checks: 
	[ok] Does not use plain HTTP. 
	[ok] NO @ hiding the real host.
	[ok] Host is not a numeric IP.

Warning signs found: 0
Caution level: LOW

Note: LinkGuard only checks a few signs in the text.

No warnings does NOT guarantee a link is safe. When in doubt, don't click.

Analyze another URL? (y/n): y
Enter a URL: http://exa mple.test
You entered: http://exa mple.test

Checks: 
	[!] Uses HTTP, not HTTPS: information sent to this site is not encrypted.
	Couldn't read that as a URL (check for spaces and odd characters).
Enter a URL: http://bank.example@192.0.2.10/login
You entered: http://bank.example@192.0.2.10/login

Checks: 
	[!] Uses HTTP, not HTTPS: information sent to this site is not encrypted.
	[!] Has an @ before the host: real destination is AFTER the @.
	[!] Uses a numeric IP address instead of a normal name.

Warning signs found: 3
Caution level: HIGH

Note: LinkGuard only checks a few signs in the text.

No warnings does NOT guarantee a link is safe. When in doubt, don't click.

Analyze another URL? (y/n): y
Enter a URL: https://198.51.100.7/
You entered: https://198.51.100.7/

Checks: 
	[ok] Does not use plain HTTP. 
	[ok] NO @ hiding the real host.
	[!] Uses a numeric IP address instead of a normal name.

Warning signs found: 1
Caution level: MEDIUM

Note: LinkGuard only checks a few signs in the text.

No warnings does NOT guarantee a link is safe. When in doubt, don't click.

Analyze another URL? (y/n): n

Goodbye! Be careful with what links you click.
...

All example addresses use reserved test domains (`.test`, `.example`) and documentation IP ranges (`192.0.2.x`, `198.51.100.x`), so none of them are real sites.

## How it works

- `Scanner` reads the URL the user types.
- `java.net.URI` splits the text into parts (scheme, authority, host, path) **without connecting to anything**.
- Each check is its own method that returns `true` or `false`, and `main` counts the warnings.
- A `while` loop lets the user check another URL or quit.

## Limitations

- Only three checks. It does not detect look-alike domains (like `examp1e.test`), shortened links, or known-bad sites.
- No warnings does **not** mean a link is safe.
- IPv6 addresses (like `[2001:db8::1]`) are not flagged as numeric IPs.
- URLs need to start with `http://` or `https://` for the host checks to work.

## What I learned

This project help me greatly in understanding the URI method and how to work with it in real programs. Before this project, I never thought about breaking up a URL into different parts and vetting each for security. This also helped me become more proficient in working with boolean values, and taught me about having to crunch deadlines while working solo.


## AI assistance

I used Claude to help me come up with a project idea, as well as to help me understand and implement working with the URI method, as a step-by-step mentor for explanations and code snippets. I typed, tested, and commented the code myself and can explain every part.