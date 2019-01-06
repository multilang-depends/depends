if $0 == __FILE__
  server = WEBrick::HTTPServer.new(:Port)
  server.mount '/', MailHandler
  trap("INT") { server.shutdown }
  server.start
end

