class X < ::C
end

class GenericHandler < WEBrick::HTTPServlet::AbstractServlet
  def do_POST(req, resp)
    d = Avro::DataFile.new(file_or_stdin(ARGV[1]), Avro::IO::DatumReader.new)
  end
end

module A::B::C
end
