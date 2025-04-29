\version "2.24.4"

\score {
  \new DrumStaff {
    \new DrumVoice {
      \drummode {
        \repeat volta 4 {
          % -- RllK 1 --
          sn16^"R"^">"
          \once \override Stem.font-size = -3
          \once \override NoteHead.font-size = -3
          \parenthesize sn16^"l"
          \once \override Stem.font-size = -3
          \once \override NoteHead.font-size = -3
          \parenthesize sn16^"l"
          bd16^"K"

          % -- RllK 2 --
          sn16^"R"^">"
          \once \override Stem.font-size = -3
          \once \override NoteHead.font-size = -3
          \parenthesize sn16^"l"
          \once \override Stem.font-size = -3
          \once \override NoteHead.font-size = -3
          \parenthesize sn16^"l"
          bd16^"K"

          % -- RLKK 1 --
          sn16^"R"^">"
          sn16^"L"^">"
          bd16^"K"
          bd16^"K"

          % -- RLKK 2 --
          sn16^"R"^">"
          sn16^"L"^">"
          bd16^"K"
          bd16^"K"
        }
      }
    }
  }
}

