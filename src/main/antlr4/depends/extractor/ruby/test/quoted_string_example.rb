class TestSchemaNormalization < Test::Unit::TestCase
  def test_primitives
    %w[null boolean string bytes int long float double].each do |type|
      canonical_form = Avro::SchemaNormalization.to_parsing_form(schema)
    end
  end


end
