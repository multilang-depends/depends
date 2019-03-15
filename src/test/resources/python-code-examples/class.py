class Foo:
    def __init__(self):
        self = self

    def to_string(self):
        return "aaaa"

class Bar (Foo):
    def __init__(self):
        self

    def to_string(self):
        return ""

