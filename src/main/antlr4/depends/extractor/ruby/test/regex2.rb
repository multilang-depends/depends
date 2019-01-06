options[:args] = orig_args.map { |s|
      s =~ /[\s|&<>$()]/ ? s.inspect : s
    }.join " "
