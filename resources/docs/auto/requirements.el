(TeX-add-style-hook
 "requirements"
 (lambda ()
   (TeX-add-to-alist 'LaTeX-provided-class-options
                     '(("article" "11pt")))
   (TeX-add-to-alist 'LaTeX-provided-package-options
                     '(("inputenc" "utf8") ("fontenc" "T1") ("ulem" "normalem")))
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "path")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "url")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "nolinkurl")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperbaseurl")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperimage")
   (add-to-list 'LaTeX-verbatim-macros-with-braces-local "hyperref")
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
    "sec:org0f30cfa"
    "sec:org1c01714"
    "sec:org021c3d6"
    "sec:org30690f9"
    "sec:org0661651"
    "sec:org3a47fa1"
    "sec:org99672ec"
    "sec:orgd58623e"
    "sec:org9985267"
    "sec:orgdc0146d"
    "sec:orgf454c8e"
    "sec:org1018240"
    "sec:orgda4abaf"
    "sec:orga22b9a4"
    "sec:orgcce5ca3"
    "sec:org3619ef9"
    "sec:org7e86b86"
    "sec:org677b0ba"
    "sec:org75b7276"
    "sec:orga70236e"
    "sec:org53b2dfd"
    "sec:orgb4fbd94"
    "sec:orgaeaf6dd"
    "sec:orgc63c060"
    "sec:org5db38b9"
    "sec:org5fc7e1f"
    "sec:org5bc1f52"
    "sec:orged79b8b"
    "sec:org15592fc"
    "sec:org1d8d64e"
    "sec:orgda2dfad"
    "sec:org7cc5376"
    "sec:org638b699"))
 :latex)

