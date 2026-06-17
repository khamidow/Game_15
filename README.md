<div align="center">

<img src="app/src/main/res/drawable/two_swords.png" width="120" alt="Puzzle 15 Logo"/>

# ⚔️ Puzzle 15

**A dark fantasy sliding puzzle — conquer the board, claim your glory.**

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)](https://www.android.com/)
[![API](https://img.shields.io/badge/Min%20SDK-24%20(Android%207.0)-blue?style=flat-square)](https://developer.android.com/about/versions/nougat)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-36-brightgreen?style=flat-square)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com/)

> *"Order from chaos. Victory from patience. Glory from stone."*

</div>

---

## 🌑 About

**Puzzle 15** is an Android incarnation of the legendary [15-puzzle](https://en.wikipedia.org/wiki/15_puzzle) — draped in dark fantasy atmosphere. Ancient runes replace sterile numbers. Sword-and-stone artwork sets the mood. A haunting ambient soundtrack pulls you deeper with every move.

The rules are simple. The mastery is not. Slide tiles into the void, restore order to the chaos, and etch your name into the leaderboard — before someone else does.

Three difficulty tiers. One empty throne. How fast can you claim it?

---

## ⚔️ Features

| Feature | Description |
|---|---|
| 🗡️ **Three Battle Tiers** | 3×3 squire · 4×4 knight · 5×5 warlord |
| ⏱️ **Live Timer** | Every second counts on the path to glory |
| 🔢 **Move Counter** | Fewer moves = greater honor |
| 🏆 **Hall of Records** | Persistent leaderboard ranked by fewest moves — your legacy, saved |
| 🎵 **Dark Ambient Score** | *Island of the Lost* plays as you battle the board |
| 🔇 **Silence the Realm** | One tap mutes the world when focus demands it |
| 💾 **Session Resume** | Leave the battlefield and return — the board remembers |
| 📜 **Portrait & Landscape** | Fully adapted layouts for however you hold your device |
| 🎲 **Solvability Seal** | Every puzzle is mathematically guaranteed winnable — no cursed boards |
| 🏅 **Victory Ceremony** | A full win screen: your time, your moves, the Hall of Records |

---

## 🕯️ The Lore of the Puzzle

> Long ago, a stone tablet was shattered into fifteen pieces and scattered across the realm.  
> Warriors from every corner have tried to restore it.  
> Most have failed. Many have returned.  
> A few — very few — have their names carved into the Hall of Records.  
>
> **You are next.**

Slide the fragments. Fill the void. Restore the tablet.

---

## 🛡️ Key Design Decisions

- **Solvability enforcement** — Before any puzzle is shown, the inversion count of the shuffled tile array is checked against the empty-tile row position. If the configuration is unsolvable, the shuffle repeats until a valid state is reached. No warrior faces an impossible quest.
- **State persistence** — `SharedPreferences` stores the full board state (tile order, empty cell position, move count, timer value) on `onStop()` and restores it on `onStart()`, surviving both app minimization and screen rotation. Your campaign is never lost.
- **Dynamic text sizing** — Tile font size is computed proportionally to button height via an `OnGlobalLayoutListener`, keeping numbers legible across all screen densities and grid sizes. The runes must always be readable.

---

## 🎮 How to Play

1. Choose your tier — **3×3** (Squire), **4×4** (Knight), or **5×5** (Warlord).
2. Tap any tile next to the empty space to slide it in.
3. Arrange all tiles in ascending order, left-to-right, top-to-bottom.
4. The empty space belongs in the **bottom-right corner** when the quest is complete.
5. Fewer moves. Less time. More glory.

---

## ⚗️ Tech Stack

| Component | Version |
|---|---|
| Android Gradle Plugin | 8.13.1 |
| Kotlin (build scripts) | 2.2.0 |
| Core KTX | 1.17.0 |
| AppCompat | 1.7.1 |
| Material Components | 1.13.0 |
| ConstraintLayout | 2.2.1 |
| Activity | 1.12.0 |
| Min SDK | 24 (Android 7.0 Nougat) |
| Target SDK | 36 |
| Java Source Compatibility | 11 |

---

## 🗺️ Getting Started

### Prerequisites

- Android Studio **Hedgehog** or later
- JDK 11+
- Android device or emulator running **API 24+**

### Clone & Enter the Realm

```bash
git clone https://github.com/your-username/GitaGame15.git
cd GitaGame15
```

Open in Android Studio, let Gradle sync, then press **Run ▶** — your campaign begins.

### Forge from the Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

The APK awaits at `app/build/outputs/apk/`.

---

## 🤝 Join the Guild

The realm grows stronger with every contributor. Open an issue, propose a feature, submit a pull request — all are welcome at the table.

1. Fork the repository
2. Create your branch: `git checkout -b feature/my-feature`
3. Commit your work: `git commit -m 'Add my feature'`
4. Push it forward: `git push origin feature/my-feature`
5. Open a Pull Request

---

<div align="center">

*Forged in Java · Tempered on Android · Released into the Dark*

⚔️ · 🏆 · 🌑

</div>
