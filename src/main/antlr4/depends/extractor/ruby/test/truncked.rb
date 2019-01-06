def self.on_signal name, action # :nodoc:
      supported = SIGNALS[name]

      old_trap = trap name do
        old_trap.call if old_trap.respond_to? :call
        action.call
      end  if supported
      yield
    ensure
      trap name, old_trap if supported
end
