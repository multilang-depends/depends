   def rubinius? platform = defined?(RUBY_ENGINE) && RUBY_ENGINE
      "rbx" == platform
    end
