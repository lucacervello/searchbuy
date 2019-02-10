(TeX-add-style-hook
 "requirements"
 (lambda ()
   (TeX-add-to-alist 'LaTeX-provided-class-options
                     '(("article" "11pt")))
   (TeX-add-to-alist 'LaTeX-provided-package-options
                     '(("inputenc" "utf8") ("fontenc" "T1") ("ulem" "normalem")))
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperref")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperimage")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperbaseurl")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "nolinkurl")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "url")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "path")
   (add-to-list 'LaTeX-verbatim-macros-with-delims-local "path")
   (TeX-run-style-hooks
    "latex2e"
    "article"
    "art11"
    "inputenc"
    "fontenc"
    "graphicx"
    "grffile"
    "longtable"
    "wrapfig"
    "rotating"
    "ulem"
    "amsmath"
    "textcomp"
    "amssymb"
    "capt-of"
    "hyperref")
   (LaTeX-add-labels
    "sec:org56c52da"
    "sec:orgfff8088"
    "sec:org2ce0bcc"
    "sec:org9e04b14"
    "sec:org6ec5006"
    "sec:orga95856c"
    "sec:org521fd37"
    "sec:orgb28f336"
    "sec:org5251284"
    "sec:org2ae61ec"
    "sec:org2a42432"
    "sec:org4ed0fe2"
    "sec:org2ca3b55"
    "sec:orge29cabb"
    "sec:org0e82d8a"
    "sec:org2ba6c75"
    "sec:orgb6f424d"
    "sec:org5fccb14"
    "sec:org930a3f8"
    "sec:orgc517a11"
    "sec:orgfe9d1ad"
    "sec:org58b03c4"
    "sec:orgaa1579a"
    "sec:org36e0b6c"
    "sec:org9f6cdc6"
    "sec:orge7abb26"
    "sec:org03063f3"
    "sec:org3bf4845"
    "sec:org04c30f2"
    "sec:orgf9cddaa"
    "sec:org396f962"
    "sec:org90e72ff"
    "sec:orgf7e03a6"))
 :latex)

