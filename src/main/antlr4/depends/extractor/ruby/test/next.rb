Gem.find_files("minitest/*_plugin.rb").each do |plugin_path|
      name = File.basename plugin_path, "_plugin.rb"

      next if seen[name]
      seen[name] = true

      require plugin_path
      self.extensions << name
    end
