test_check "assignment"

a=[]; a[0] ||= "bar";
test_ok(a[0] == "bar")
h={}; h["foo"] ||= "bar";
test_ok(h["foo"] == "bar")
