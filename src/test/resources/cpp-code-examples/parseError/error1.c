void foo() {
gimp_dialog_new (_("Exercise a goat (C)"), PLUG_IN_ROLE,
                                NULL, GTK_DIALOG_USE_HEADER_BAR,
                                gimp_standard_help_func, PLUG_IN_PROC,

                                _("_Cancel"), GTK_RESPONSE_CANCEL,
                                _("_Source"), GTK_RESPONSE_APPLY,
                                _("_Run"),    GTK_RESPONSE_OK,

                                NULL);
}
