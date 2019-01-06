    @tty = STDERR.tty? && !STDOUT.tty? && !~ ENV["TERM"] if @tty.nil?
    @eol = @tty && !@verbose ? "\r\e[K\r" : "\n"
