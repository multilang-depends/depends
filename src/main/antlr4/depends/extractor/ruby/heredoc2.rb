      schema = Avro::Schema.parse(<<-JSON)
        { "type": "#{type}" }
      JSON

expected_type = <<-JSON.strip
      {"name":"cards.suit","type":"enum","symbols":["club","hearts","diamond","spades"]}
    JSON

