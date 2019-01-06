    unless options[:seed] then
      srand
      options[:seed] = (ENV["SEED"] || srand).to_i % 0xFFFF
      orig_args << "--seed" << options[:seed].to_s
    end

