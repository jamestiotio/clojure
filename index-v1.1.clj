{:namespaces
 ({:doc "Fundamental library of the Clojure language",
   :name "clojure.core",
   :wiki-url "http://clojure.github.io/clojure/clojure.core-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj"}
  {:doc "Graphical object inspector for Clojure data structures.",
   :author "Rich Hickey",
   :name "clojure.inspector",
   :wiki-url
   "http://clojure.github.io/clojure/clojure.inspector-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/inspector.clj"}
  {:doc nil,
   :name "clojure.main",
   :wiki-url "http://clojure.github.io/clojure/clojure.main-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj"}
  {:doc "Set operations such as union/intersection.",
   :author "Rich Hickey",
   :name "clojure.set",
   :wiki-url "http://clojure.github.io/clojure/clojure.set-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj"}
  {:doc "Print stack traces oriented towards Clojure, not Java.",
   :author "Stuart Sierra",
   :name "clojure.stacktrace",
   :wiki-url
   "http://clojure.github.io/clojure/clojure.stacktrace-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj"}
  {:doc
   "Macros that expand to repeated copies of a template expression.",
   :author "Stuart Sierra",
   :name "clojure.template",
   :wiki-url
   "http://clojure.github.io/clojure/clojure.template-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/template.clj"}
  {:doc
   "A unit testing framework.\n\nASSERTIONS\n\nThe core of the library is the \"is\" macro, which lets you make\nassertions of any arbitrary expression:\n\n(is (= 4 (+ 2 2)))\n(is (instance? Integer 256))\n(is (.startsWith \"abcde\" \"ab\"))\n\nYou can type an \"is\" expression directly at the REPL, which will\nprint a message if it fails.\n\n    user> (is (= 5 (+ 2 2)))\n\n    FAIL in  (:1)\n    expected: (= 5 (+ 2 2))\n      actual: (not (= 5 4))\n    false\n\nThe \"expected:\" line shows you the original expression, and the\n\"actual:\" shows you what actually happened.  In this case, it\nshows that (+ 2 2) returned 4, which is not = to 5.  Finally, the\n\"false\" on the last line is the value returned from the\nexpression.  The \"is\" macro always returns the result of the\ninner expression.\n\nThere are two special assertions for testing exceptions.  The\n\"(is (thrown? c ...))\" form tests if an exception of class c is\nthrown:\n\n(is (thrown? ArithmeticException (/ 1 0))) \n\n\"(is (thrown-with-msg? c re ...))\" does the same thing and also\ntests that the message on the exception matches the regular\nexpression re:\n\n(is (thrown-with-msg? ArithmeticException #\"Divide by zero\"\n                      (/ 1 0)))\n\nDOCUMENTING TESTS\n\n\"is\" takes an optional second argument, a string describing the\nassertion.  This message will be included in the error report.\n\n(is (= 5 (+ 2 2)) \"Crazy arithmetic\")\n\nIn addition, you can document groups of assertions with the\n\"testing\" macro, which takes a string followed by any number of\nassertions.  The string will be included in failure reports.\nCalls to \"testing\" may be nested, and all of the strings will be\njoined together with spaces in the final report, in a style\nsimilar to RSpec <http://rspec.info/>\n\n(testing \"Arithmetic\"\n  (testing \"with positive integers\"\n    (is (= 4 (+ 2 2)))\n    (is (= 7 (+ 3 4))))\n  (testing \"with negative integers\"\n    (is (= -4 (+ -2 -2)))\n    (is (= -1 (+ 3 -4)))))\n\nNote that, unlike RSpec, the \"testing\" macro may only be used\nINSIDE a \"deftest\" or \"with-test\" form (see below).\n\n\nDEFINING TESTS\n\nThere are two ways to define tests.  The \"with-test\" macro takes\na defn or def form as its first argument, followed by any number\nof assertions.  The tests will be stored as metadata on the\ndefinition.\n\n(with-test\n    (defn my-function [x y]\n      (+ x y))\n  (is (= 4 (my-function 2 2)))\n  (is (= 7 (my-function 3 4))))\n\nAs of Clojure SVN rev. 1221, this does not work with defmacro.\nSee http://code.google.com/p/clojure/issues/detail?id=51\n\nThe other way lets you define tests separately from the rest of\nyour code, even in a different namespace:\n\n(deftest addition\n  (is (= 4 (+ 2 2)))\n  (is (= 7 (+ 3 4))))\n\n(deftest subtraction\n  (is (= 1 (- 4 3)))\n  (is (= 3 (- 7 4))))\n\nThis creates functions named \"addition\" and \"subtraction\", which\ncan be called like any other function.  Therefore, tests can be\ngrouped and composed, in a style similar to the test framework in\nPeter Seibel's \"Practical Common Lisp\"\n<http://www.gigamonkeys.com/book/practical-building-a-unit-test-framework.html>\n\n(deftest arithmetic\n  (addition)\n  (subtraction))\n\nThe names of the nested tests will be joined in a list, like\n\"(arithmetic addition)\", in failure reports.  You can use nested\ntests to set up a context shared by several tests.\n\n\nRUNNING TESTS\n\nRun tests with the function \"(run-tests namespaces...)\":\n\n(run-tests 'your.namespace 'some.other.namespace)\n\nIf you don't specify any namespaces, the current namespace is\nused.  To run all tests in all namespaces, use \"(run-all-tests)\".\n\nBy default, these functions will search for all tests defined in\na namespace and run them in an undefined order.  However, if you\nare composing tests, as in the \"arithmetic\" example above, you\nprobably do not want the \"addition\" and \"subtraction\" tests run\nseparately.  In that case, you must define a special function\nnamed \"test-ns-hook\" that runs your tests in the correct order:\n\n(defn test-ns-hook []\n  (arithmetic))\n\n\nOMITTING TESTS FROM PRODUCTION CODE\n\nYou can bind the variable \"*load-tests*\" to false when loading or\ncompiling code in production.  This will prevent any tests from\nbeing created by \"with-test\" or \"deftest\".\n\n\nFIXTURES (new)\n\nFixtures allow you to run code before and after tests, to set up\nthe context in which tests should be run.\n\nA fixture is just a function that calls another function passed as\nan argument.  It looks like this:\n\n(defn my-fixture [f]\n   Perform setup, establish bindings, whatever.\n  (f)  Then call the function we were passed.\n   Tear-down / clean-up code here.\n )\n\nFixtures are attached to namespaces in one of two ways.  \"each\"\nfixtures are run repeatedly, once for each test function created\nwith \"deftest\" or \"with-test\".  \"each\" fixtures are useful for\nestablishing a consistent before/after state for each test, like\nclearing out database tables.\n\n\"each\" fixtures can be attached to the current namespace like this:\n(use-fixtures :each fixture1 fixture2 ...)\nThe fixture1, fixture2 are just functions like the example above.\nThey can also be anonymous functions, like this:\n(use-fixtures :each (fn [f] setup... (f) cleanup...))\n\nThe other kind of fixture, a \"once\" fixture, is only run once,\naround ALL the tests in the namespace.  \"once\" fixtures are useful\nfor tasks that only need to be performed once, like establishing\ndatabase connections, or for time-consuming tasks.\n\nAttach \"once\" fixtures to the current namespace like this:\n(use-fixtures :once fixture1 fixture2 ...)\n\n\nSAVING TEST OUTPUT TO A FILE\n\nAll the test reporting functions write to the var *test-out*.  By\ndefault, this is the same as *out*, but you can rebind it to any\nPrintWriter.  For example, it could be a file opened with\nclojure.contrib.duck-streams/writer.\n\n\nEXTENDING TEST-IS (ADVANCED)\n\nYou can extend the behavior of the \"is\" macro by defining new\nmethods for the \"assert-expr\" multimethod.  These methods are\ncalled during expansion of the \"is\" macro, so they should return\nquoted forms to be evaluated.\n\nYou can plug in your own test-reporting framework by rebinding\nthe \"report\" function: (report event)\n\nThe 'event' argument is a map.  It will always have a :type key,\nwhose value will be a keyword signaling the type of event being\nreported.  Standard events with :type value of :pass, :fail, and\n:error are called when an assertion passes, fails, and throws an\nexception, respectively.  In that case, the event will also have\nthe following keys:\n\n  :expected   The form that was expected to be true\n  :actual     A form representing what actually occurred\n  :message    The string message given as an argument to 'is'\n\nThe \"testing\" strings will be a list in \"*testing-contexts*\", and\nthe vars being tested will be a list in \"*testing-vars*\".\n\nYour \"report\" function should wrap any printing calls in the\n\"with-test-out\" macro, which rebinds *out* to the current value\nof *test-out*.\n\nFor additional event types, see the examples in the code.",
   :author
   "Stuart Sierra, with contributions and suggestions by \n  Chas Emerick, Allen Rohner, and Stuart Halloway",
   :name "clojure.test",
   :wiki-url "http://clojure.github.io/clojure/clojure.test-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj"}
  {:doc
   "This file defines a generic tree walker for Clojure data\nstructures.  It takes any data structure (list, vector, map, set,\nseq), calls a function on every element, and uses the return value\nof the function in place of the original.  This makes it fairly\neasy to write recursive search-and-replace functions, as shown in\nthe examples.\n\nNote: \"walk\" supports all Clojure data structures EXCEPT maps\ncreated with sorted-map-by.  There is no (obvious) way to retrieve\nthe sorting function.",
   :author "Stuart Sierra",
   :name "clojure.walk",
   :wiki-url "http://clojure.github.io/clojure/clojure.walk-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj"}
  {:doc "XML reading/writing.",
   :author "Rich Hickey",
   :name "clojure.xml",
   :wiki-url "http://clojure.github.io/clojure/clojure.xml-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/xml.clj"}
  {:doc
   "Functional hierarchical zipper, with navigation, editing, \nand enumeration.  See Huet",
   :author "Rich Hickey",
   :name "clojure.zip",
   :wiki-url "http://clojure.github.io/clojure/clojure.zip-api.html",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj"}
  {:doc
   "clojure.test extension for JUnit-compatible XML output.\n\nJUnit (http://junit.org/) is the most popular unit-testing library\nfor Java.  As such, tool support for JUnit output formats is\ncommon.  By producing compatible output from tests, this tool\nsupport can be exploited.\n\nTo use, wrap any calls to clojure.test/run-tests in the\nwith-junit-output macro, like this:\n\n  (use 'clojure.test)\n  (use 'clojure.contrib.test.junit)\n\n  (with-junit-output\n    (run-tests 'my.cool.library))\n\nTo write the output to a file, rebind clojure.test/*test-out* to\nyour own PrintWriter (perhaps opened using\nclojure.contrib.duck-streams/writer).",
   :author "Jason Sankey",
   :name "clojure.test.junit",
   :wiki-url
   "http://clojure.github.io/clojure/clojure.test-api.html#clojure.test.junit",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/junit.clj"}
  {:doc
   "clojure.test extensions for the Test Anything Protocol (TAP)\n\nTAP is a simple text-based syntax for reporting test results.  TAP\nwas originally develped for Perl, and now has implementations in\nseveral languages.  For more information on TAP, see\nhttp://testanything.org/ and\nhttp://search.cpan.org/~petdance/TAP-1.0.0/TAP.pm\n\nTo use this library, wrap any calls to\nclojure.test/run-tests in the with-tap-output macro,\nlike this:\n\n  (use 'clojure.test)\n  (use 'clojure.test.tap)\n\n  (with-tap-output\n   (run-tests 'my.cool.library))",
   :author "Stuart Sierra",
   :name "clojure.test.tap",
   :wiki-url
   "http://clojure.github.io/clojure/clojure.test-api.html#clojure.test.tap",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj"}),
 :vars
 ({:raw-source-url nil,
   :added "1.0",
   :name "&",
   :file nil,
   :source-url nil,
   :var-type "special syntax",
   :arglists nil,
   :doc
   "Syntax for use with fn.\n\nPlease see http://clojure.org/special_forms#fn",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/&"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "*",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L687",
   :line 687,
   :var-type "function",
   :arglists ([] [x] [x y] [x y & more]),
   :doc "Returns the product of nums. (*) returns 1.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "*1",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4259",
   :line 4259,
   :var-type "var",
   :arglists nil,
   :doc "bound in a repl thread to the most recent value printed",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*1"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "*2",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4263",
   :line 4263,
   :var-type "var",
   :arglists nil,
   :doc
   "bound in a repl thread to the second most recent value printed",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*2"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "*3",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4267",
   :line 4267,
   :var-type "var",
   :arglists nil,
   :doc
   "bound in a repl thread to the third most recent value printed",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*3"}
  {:name "*agent*",
   :doc
   "The agent currently running an action on this thread, else nil",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*agent*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "*clojure-version*",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4552",
   :line 4552,
   :var-type "var",
   :arglists nil,
   :doc
   "The version info for Clojure core, as a map containing :major :minor \n:incremental and :qualifier keys. Feature releases may increment \n:minor and/or :major, bugfix releases will increment :incremental. \nPossible values of :qualifier include \"GA\", \"SNAPSHOT\", \"RC-x\" \"BETA-x\"",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*clojure-version*"}
  {:name "*command-line-args*",
   :doc
   "A sequence of the supplied command line arguments, or nil if\nnone were supplied",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*command-line-args*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*compile-files*",
   :doc "Set to true when compiling files, false otherwise.",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*compile-files*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*compile-path*",
   :doc
   "Specifies the directory where 'compile' will write out .class\nfiles. This directory must be in the classpath for 'compile' to\nwork.\n\nDefaults to \"classes\"",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*compile-path*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "*e",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4271",
   :line 4271,
   :var-type "var",
   :arglists nil,
   :doc
   "bound in a repl thread to the most recent exception caught by the repl",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*e"}
  {:name "*err*",
   :doc
   "A java.io.Writer object representing standard error for print operations.\n\nDefaults to System/err, wrapped in a PrintWriter",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*err*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*file*",
   :doc
   "The path of the file being evaluated, as a String.\n\nEvaluates to nil when there is no file, eg. in the REPL.",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*file*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*flush-on-newline*",
   :doc
   "When set to true, output will be flushed whenever a newline is printed.\n\nDefaults to true.",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*flush-on-newline*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*in*",
   :doc
   "A java.io.Reader object representing standard input for read operations.\n\nDefaults to System/in, wrapped in a LineNumberingPushbackReader",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*in*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*ns*",
   :doc
   "A clojure.lang.Namespace object representing the current namespace.",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*ns*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*out*",
   :doc
   "A java.io.Writer object representing standard output for print operations.\n\nDefaults to System/out",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*out*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*print-dup*",
   :doc
   "When set to logical true, objects will be printed in a way that preserves\ntheir type when read in later.\n\nDefaults to false.",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*print-dup*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj",
   :name "*print-length*",
   :file "src/clj/clojure/core_print.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj#L15",
   :line 15,
   :var-type "var",
   :arglists nil,
   :doc
   "*print-length* controls how many items of each collection the\nprinter will print. If it is bound to logical false, there is no\nlimit. Otherwise, it must be bound to an integer indicating the maximum\nnumber of items of each collection to print. If a collection contains\nmore items, the printer will print items up to the limit followed by\n'...' to represent the remaining items. The root binding is nil\nindicating no limit.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*print-length*"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj",
   :name "*print-level*",
   :file "src/clj/clojure/core_print.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj#L25",
   :line 25,
   :var-type "var",
   :arglists nil,
   :doc
   "*print-level* controls how many levels deep the printer will\nprint nested objects. If it is bound to logical false, there is no\nlimit. Otherwise, it must be bound to an integer indicating the maximum\nlevel to print. Each argument to print is at level 0; if an argument is a\ncollection, its items are at level 1; and so on. If an object is a\ncollection and is at a level greater than or equal to the value bound to\n*print-level*, the printer prints '#' to represent it. The root binding\nis nil indicating no limit.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*print-level*"}
  {:name "*print-meta*",
   :doc
   "If set to logical true, when printing an object, its metadata will also\nbe printed in a form that can be read back by the reader.\n\nDefaults to false.",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*print-meta*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*print-readably*",
   :doc
   "When set to logical false, strings and characters will be printed with\nnon-alphanumeric characters converted to the appropriate escape sequences.\n\nDefaults to true",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*print-readably*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*read-eval*",
   :doc
   "When set to logical false, the EvalReader (#=(...)) is disabled in the \nread/load in the thread-local binding.\nExample: (binding [*read-eval* false] (read-string \"#=(eval (def x 3))\"))\n\nDefaults to true",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*read-eval*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:name "*warn-on-reflection*",
   :doc
   "When set to true, the compiler will emit warnings when reflection is\nneeded to resolve Java method calls or field accesses.\n\nDefaults to false.",
   :var-type "var",
   :namespace "clojure.core",
   :arglists nil,
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/*warn-on-reflection*",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "+",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L677",
   :line 677,
   :var-type "function",
   :arglists ([] [x] [x y] [x y & more]),
   :doc "Returns the sum of nums. (+) returns 0.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/+"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "-",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L707",
   :line 707,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "If no ys are supplied, returns the negation of x, else subtracts\nthe ys from x and returns the result.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/-"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "->",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1089",
   :line 1089,
   :var-type "macro",
   :arglists ([x] [x form] [x form & more]),
   :doc
   "Threads the expr through the forms. Inserts x as the\nsecond item in the first form, making a list of it if it is not a\nlist already. If there are more forms, inserts the first form as the\nsecond item in second form, etc.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/->"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "->>",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1100",
   :line 1100,
   :var-type "macro",
   :arglists ([x form] [x form & more]),
   :doc
   "Threads the expr through the forms. Inserts x as the\nlast item in the first form, making a list of it if it is not a\nlist already. If there are more forms, inserts the first form as the\nlast item in second form, etc.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/->>"}
  {:raw-source-url nil,
   :added "1.0",
   :name ".",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "The instance member form works for both fields and methods.\nThey all expand into calls to the dot operator at macroexpansion time.\n\nPlease see http://clojure.org/java_interop#dot",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/.",
   :forms
   [(.instanceMember instance args*)
    (.instanceMember Classname args*)
    (Classname/staticMethod args*)
    Classname/staticField]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "..",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1072",
   :line 1072,
   :var-type "macro",
   :arglists ([x form] [x form & more]),
   :doc
   "form => fieldName-symbol or (instanceMethodName-symbol args*)\n\nExpands into a member access (.) of the first member on the first\nargument, followed by the next member on the result, etc. For\ninstance:\n\n(.. System (getProperties) (get \"os.name\"))\n\nexpands to:\n\n(. (. System (getProperties)) (get \"os.name\"))\n\nbut is easier to write, read, and understand.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/.."}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "/",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L697",
   :line 697,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "If no denominators are supplied, returns 1/numerator,\nelse returns numerator divided by all of the denominators.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core//"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "<",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L627",
   :line 627,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "Returns non-nil if nums are in monotonically increasing order,\notherwise false.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/<"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "<=",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L717",
   :line 717,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "Returns non-nil if nums are in monotonically non-decreasing order,\notherwise false.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/<="}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "=",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L539",
   :line 539,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "Equality. Returns true if x equals y, false if not. Same as\nJava x.equals(y) except it also works for nil, and compares\nnumbers and collections in a type-independent manner.  Clojure's immutable data\nstructures define equals() (and thus =) as a value, not an identity,\ncomparison.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/="}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "==",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L759",
   :line 759,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "Returns non-nil if nums all have the same value, otherwise false",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/=="}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name ">",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L731",
   :line 731,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "Returns non-nil if nums are in monotonically decreasing order,\notherwise false.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/>"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name ">=",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L745",
   :line 745,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc
   "Returns non-nil if nums are in monotonically non-increasing order,\notherwise false.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/>="}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "accessor",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2612",
   :line 2612,
   :var-type "function",
   :arglists ([s key]),
   :doc
   "Returns a fn that, given an instance of a structmap with the basis,\nreturns the value at the key.  The key must be in the basis. The\nreturned function should be (slightly) more efficient than using\nget, but such use of accessors should be limited to known\nperformance-critical areas.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/accessor"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aclone",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2473",
   :line 2473,
   :var-type "function",
   :arglists ([array]),
   :doc
   "Returns a clone of the Java array. Works on arrays of known\ntypes.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aclone"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "add-classpath",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3472",
   :line 3472,
   :var-type "function",
   :arglists ([url]),
   :doc
   "DEPRECATED \n\nAdds the url (String or URL object) to the classpath per\nURLClassLoader.addURL",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/add-classpath"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "add-watch",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1371",
   :line 1371,
   :var-type "function",
   :arglists ([reference key fn]),
   :doc
   "Alpha - subject to change.\nAdds a watch function to an agent/atom/var/ref reference. The watch\nfn must be a fn of 4 args: a key, the reference, its old-state, its\nnew-state. Whenever the reference's state might have been changed,\nany registered watches will have their functions called. The watch fn\nwill be called synchronously, on the agent's thread if an agent,\nbefore any pending sends if agent or ref. Note that an atom's or\nref's state may have changed again prior to the fn call, so use\nold/new-state rather than derefing the reference. Note also that watch\nfns may be called from multiple threads simultaneously. Var watchers\nare triggered only by root binding changes, not thread-local\nset!s. Keys must be unique per reference, and can be used to remove\nthe watch with remove-watch, but are otherwise considered opaque by\nthe watch mechanism.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/add-watch"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "agent",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1327",
   :line 1327,
   :var-type "function",
   :arglists ([state] [state & options]),
   :doc
   "Creates and returns an agent with an initial value of state and\nzero or more options (in any order):\n\n:meta metadata-map\n\n:validator validate-fn\n\nIf metadata-map is supplied, it will be come the metadata on the\nagent. validate-fn must be nil or a side-effect-free fn of one\nargument, which will be passed the intended new state on any state\nchange. If the new state is unacceptable, the validate-fn should\nreturn false or throw an exception.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/agent"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "agent-errors",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1395",
   :line 1395,
   :var-type "function",
   :arglists ([a]),
   :doc
   "Returns a sequence of the exceptions thrown during asynchronous\nactions of the agent.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/agent-errors"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aget",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2479",
   :line 2479,
   :var-type "function",
   :arglists ([array idx] [array idx & idxs]),
   :doc
   "Returns the value at the index/indices. Works on Java arrays of all\ntypes.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aget"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "alength",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2467",
   :line 2467,
   :var-type "function",
   :arglists ([array]),
   :doc
   "Returns the length of the Java array. Works on arrays of all\ntypes.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/alength"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "alias",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2753",
   :line 2753,
   :var-type "function",
   :arglists ([alias namespace-sym]),
   :doc
   "Add an alias in the current namespace to another\nnamespace. Arguments are two symbols: the alias to be used, and\nthe symbolic name of the target namespace. Use :as in the ns macro in preference\nto calling this directly.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/alias"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "all-ns",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2662",
   :line 2662,
   :var-type "function",
   :arglists ([]),
   :doc "Returns a sequence of all namespaces.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/all-ns"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "alter",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1534",
   :line 1534,
   :var-type "function",
   :arglists ([ref fun & args]),
   :doc
   "Must be called in a transaction. Sets the in-transaction-value of\nref to:\n\n(apply fun in-transaction-value-of-ref args)\n\nand returns the in-transaction-value of ref.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/alter"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "alter-meta!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1503",
   :line 1503,
   :var-type "function",
   :arglists ([iref f & args]),
   :doc
   "Atomically sets the metadata for a namespace/var/ref/agent/atom to be:\n\n(apply f its-current-meta args)\n\nf must be free of side-effects",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/alter-meta!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "alter-var-root",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3664",
   :line 3664,
   :var-type "function",
   :arglists ([v f & args]),
   :doc
   "Atomically alters the root binding of var v by applying f to its\ncurrent value plus any args",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/alter-var-root"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "amap",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3508",
   :line 3508,
   :var-type "macro",
   :arglists ([a idx ret expr]),
   :doc
   "Maps an expression across an array a, using an index named idx, and\nreturn value named ret, initialized to a clone of a, then setting \neach element of ret to the evaluation of expr, returning the new \narray ret.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/amap"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ancestors",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3729",
   :line 3729,
   :var-type "function",
   :arglists ([tag] [h tag]),
   :doc
   "Returns the immediate and indirect parents of tag, either via a Java type\ninheritance relationship or a relationship established via derive. h\nmust be a hierarchy obtained from make-hierarchy, if not supplied\ndefaults to the global hierarchy",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ancestors"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "and",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L577",
   :line 577,
   :var-type "macro",
   :arglists ([] [x] [x & next]),
   :doc
   "Evaluates exprs one at a time, from left to right. If a form\nreturns logical false (nil or false), and returns that value and\ndoesn't evaluate any of the other expressions, otherwise it returns\nthe value of the last expr. (and) returns true.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/and"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "apply",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L432",
   :line 432,
   :var-type "function",
   :arglists ([f args* argseq]),
   :doc
   "Applies fn f to the argument list formed by prepending args to argseq.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/apply"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "areduce",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3523",
   :line 3523,
   :var-type "macro",
   :arglists ([a idx ret init expr]),
   :doc
   "Reduces an expression across an array a, using an index named idx,\nand return value named ret, initialized to init, setting ret to the \nevaluation of expr at each step, returning ret.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/areduce"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "array-map",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2831",
   :line 2831,
   :var-type "function",
   :arglists ([] [& keyvals]),
   :doc "Constructs an array-map.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/array-map"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2489",
   :line 2489,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on Java arrays of\nreference types. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-boolean",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2519",
   :line 2519,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of boolean. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-boolean"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-byte",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2535",
   :line 2535,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of byte. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-byte"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-char",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2539",
   :line 2539,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of char. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-char"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-double",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2527",
   :line 2527,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of double. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-double"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-float",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2523",
   :line 2523,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of float. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-float"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-int",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2511",
   :line 2511,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of int. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-int"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-long",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2515",
   :line 2515,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of long. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-long"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "aset-short",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2531",
   :line 2531,
   :var-type "function",
   :arglists ([array idx val] [array idx idx2 & idxv]),
   :doc
   "Sets the value at the index/indices. Works on arrays of short. Returns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/aset-short"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "assert",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3149",
   :line 3149,
   :var-type "macro",
   :arglists ([x]),
   :doc
   "Evaluates expr and throws an exception if it does not evaluate to\nlogical true.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/assert"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "assoc",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L138",
   :line 138,
   :var-type "function",
   :arglists ([map key val] [map key val & kvs]),
   :doc
   "assoc[iate]. When applied to a map, returns a new map of the\nsame (hashed/sorted) type, that contains the mapping of key(s) to\nval(s). When applied to a vector, returns a new vector that\ncontains val at index. Note - index must be <= (count vector).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/assoc"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "assoc!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4624",
   :line 4624,
   :var-type "function",
   :arglists ([coll key val] [coll key val & kvs]),
   :doc
   "Alpha - subject to change.\nWhen applied to a transient map, adds mapping of key(s) to\nval(s). When applied to a transient vector, sets the val at index.\nNote - index must be <= (count vector). Returns coll.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/assoc!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "assoc-in",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4191",
   :line 4191,
   :var-type "function",
   :arglists ([m [k & ks] v]),
   :doc
   "Associates a value in a nested associative structure, where ks is a\nsequence of keys and v is the new value and returns a new nested structure.\nIf any levels do not exist, hash-maps will be created.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/assoc-in"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "associative?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4239",
   :line 4239,
   :var-type "function",
   :arglists ([coll]),
   :doc "Returns true if coll implements Associative",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/associative?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "atom",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1453",
   :line 1453,
   :var-type "function",
   :arglists ([x] [x & options]),
   :doc
   "Creates and returns an Atom with an initial value of x and zero or\nmore options (in any order):\n\n:meta metadata-map\n\n:validator validate-fn\n\nIf metadata-map is supplied, it will be come the metadata on the\natom. validate-fn must be nil or a side-effect-free fn of one\nargument, which will be passed the intended new state on any state\nchange. If the new state is unacceptable, the validate-fn should\nreturn false or throw an exception.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/atom"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "await",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2103",
   :line 2103,
   :var-type "function",
   :arglists ([& agents]),
   :doc
   "Blocks the current thread (indefinitely!) until all actions\ndispatched thus far, from this thread or agent, to the agent(s) have\noccurred.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/await"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "await-for",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2122",
   :line 2122,
   :var-type "function",
   :arglists ([timeout-ms & agents]),
   :doc
   "Blocks the current thread until all actions dispatched thus\nfar (from this thread or agent) to the agents have occurred, or the\ntimeout (in milliseconds) has elapsed. Returns nil if returning due\nto timeout, non-nil otherwise.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/await-for"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bases",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3680",
   :line 3680,
   :var-type "function",
   :arglists ([c]),
   :doc
   "Returns the immediate superclass and direct interfaces of c, if any",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bases"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "bean",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L360",
   :line 360,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Takes a Java object and returns a read-only implementation of the\nmap abstraction based upon its JavaBean properties.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bean"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bigdec",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2301",
   :line 2301,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to BigDecimal",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bigdec"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bigint",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2292",
   :line 2292,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to BigInteger",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bigint"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "binding",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1251",
   :line 1251,
   :var-type "macro",
   :arglists ([bindings & body]),
   :doc
   "binding => var-symbol init-expr\n\nCreates new bindings for the (already-existing) vars, with the\nsupplied initial values, executes the exprs in an implicit do, then\nre-establishes the bindings that existed before.  The new bindings\nare made in parallel (unlike let); all init-exprs are evaluated\nbefore the vars are bound to their new values.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/binding"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-and",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L874",
   :line 874,
   :var-type "function",
   :arglists ([x y]),
   :doc "Bitwise and",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-and"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-and-not",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L889",
   :line 889,
   :var-type "function",
   :arglists ([x y]),
   :doc "Bitwise and with complement",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-and-not"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-clear",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L894",
   :line 894,
   :var-type "function",
   :arglists ([x n]),
   :doc "Clear bit at index n",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-clear"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-flip",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L902",
   :line 902,
   :var-type "function",
   :arglists ([x n]),
   :doc "Flip bit at index n",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-flip"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-not",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L868",
   :line 868,
   :var-type "function",
   :arglists ([x]),
   :doc "Bitwise complement",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-not"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-or",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L879",
   :line 879,
   :var-type "function",
   :arglists ([x y]),
   :doc "Bitwise or",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-or"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-set",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L898",
   :line 898,
   :var-type "function",
   :arglists ([x n]),
   :doc "Set bit at index n",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-set"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-shift-left",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L911",
   :line 911,
   :var-type "function",
   :arglists ([x n]),
   :doc "Bitwise shift left",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-shift-left"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-shift-right",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L915",
   :line 915,
   :var-type "function",
   :arglists ([x n]),
   :doc "Bitwise shift right",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-shift-right"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-test",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L906",
   :line 906,
   :var-type "function",
   :arglists ([x n]),
   :doc "Test bit at index n",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-test"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bit-xor",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L884",
   :line 884,
   :var-type "function",
   :arglists ([x y]),
   :doc "Bitwise exclusive or",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bit-xor"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "boolean",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2246",
   :line 2246,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to boolean",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/boolean"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "boolean-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3541",
   :line 3541,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of booleans",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/boolean-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "booleans",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3590",
   :line 3590,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to boolean[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/booleans"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bound-fn",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1304",
   :line 1304,
   :var-type "macro",
   :arglists ([& fntail]),
   :doc
   "Returns a function defined by the given fntail, which will install the\nsame bindings in effect as in the thread at the time bound-fn was called.\nThis may be used to define a helper function which runs on a different\nthread, but needs the same bindings in place.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bound-fn"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bound-fn*",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1294",
   :line 1294,
   :var-type "function",
   :arglists ([f]),
   :doc
   "Returns a function, which will install the same bindings in effect as in\nthe thread at the time bound-fn* was called and then call f with any given\narguments. This may be used to define a helper function which runs on a\ndifferent thread, but needs the same bindings in place.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bound-fn*"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "butlast",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L197",
   :line 197,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Return a seq of all but the last item in coll, in linear time",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/butlast"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "byte",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2234",
   :line 2234,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to byte",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/byte"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "byte-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3548",
   :line 3548,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of bytes",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/byte-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "bytes",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3594",
   :line 3594,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to bytes[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/bytes"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "cast",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L250",
   :line 250,
   :var-type "function",
   :arglists ([c x]),
   :doc "Throws a ClassCastException if x is not a c, else returns x.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/cast"}
  {:raw-source-url nil,
   :added "1.0",
   :name "catch",
   :file nil,
   :source-url nil,
   :var-type "special syntax",
   :arglists nil,
   :doc
   "Syntax for use with try.\n\nPlease see http://clojure.org/special_forms#try",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/catch"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "char",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2240",
   :line 2240,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to char",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/char"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "char-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3555",
   :line 3555,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of chars",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/char-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj",
   :name "char-escape-string",
   :file "src/clj/clojure/core_print.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj#L165",
   :line 165,
   :var-type "var",
   :arglists nil,
   :doc "Returns escape string for char or nil if none",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/char-escape-string"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj",
   :name "char-name-string",
   :file "src/clj/clojure/core_print.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/1a0e23d0e78ef3d3a3a6267a68adcfc414d3fb56/src/clj/clojure/core_print.clj#L223",
   :line 223,
   :var-type "var",
   :arglists nil,
   :doc "Returns name string for char or nil if none",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/char-name-string"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "char?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L118",
   :line 118,
   :var-type "function",
   :arglists ([x]),
   :doc "Return true if x is a Character",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/char?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "chars",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3598",
   :line 3598,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to chars[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/chars"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "class",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2195",
   :line 2195,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns the Class of x",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/class"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "class?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3660",
   :line 3660,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is an instance of Class",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/class?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "clear-agent-errors",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1400",
   :line 1400,
   :var-type "function",
   :arglists ([a]),
   :doc
   "Clears any exceptions thrown during asynchronous actions of the\nagent, allowing subsequent actions to occur.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/clear-agent-errors"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "clojure-version",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4563",
   :line 4563,
   :var-type "function",
   :arglists ([]),
   :doc "Returns clojure version as a printable string.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/clojure-version"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "coll?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4217",
   :line 4217,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x implements IPersistentCollection",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/coll?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "comment",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3099",
   :line 3099,
   :var-type "macro",
   :arglists ([& body]),
   :doc "Ignores body, yields nil",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/comment"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "commute",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1515",
   :line 1515,
   :var-type "function",
   :arglists ([ref fun & args]),
   :doc
   "Must be called in a transaction. Sets the in-transaction-value of\nref to:\n\n(apply fun in-transaction-value-of-ref args)\n\nand returns the in-transaction-value of ref.\n\nAt the commit point of the transaction, sets the value of ref to be:\n\n(apply fun most-recently-committed-value-of-ref args)\n\nThus fun should be commutative, or, failing that, you must accept\nlast-one-in-wins behavior.  commute allows for more concurrency than\nref-set.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/commute"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "comp",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1605",
   :line 1605,
   :var-type "function",
   :arglists ([f] [f g] [f g h] [f1 f2 f3 & fs]),
   :doc
   "Takes a set of functions and returns a fn that is the composition\nof those fns.  The returned fn takes a variable number of args,\napplies the rightmost of fns to the args, the next\nfn (right-to-left) to the result, etc.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/comp"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "comparator",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1962",
   :line 1962,
   :var-type "function",
   :arglists ([pred]),
   :doc
   "Returns an implementation of java.util.Comparator based upon pred.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/comparator"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "compare",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L567",
   :line 567,
   :var-type "function",
   :arglists ([x y]),
   :doc
   "Comparator. Returns a negative number, zero, or a positive number\nwhen x is logically 'less than', 'equal to', or 'greater than'\ny. Same as Java x.compareTo(y) except it also works for nil, and\ncompares numbers and collections in a type-independent manner. x\nmust implement Comparable",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/compare"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "compare-and-set!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1479",
   :line 1479,
   :var-type "function",
   :arglists ([atom oldval newval]),
   :doc
   "Atomically sets the value of atom to newval if and only if the\ncurrent value of the atom is identical to oldval. Returns true if\nset happened, else false",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/compare-and-set!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "compile",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4173",
   :line 4173,
   :var-type "function",
   :arglists ([lib]),
   :doc
   "Compiles the namespace named by the symbol lib into a set of\nclassfiles. The source for the lib must be in a proper\nclasspath-relative directory. The output files will go into the\ndirectory specified by *compile-path*, and that directory too must\nbe in the classpath.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/compile"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "complement",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L930",
   :line 930,
   :var-type "function",
   :arglists ([f]),
   :doc
   "Takes a fn f and returns a fn that takes the same arguments as f,\nhas the same effects, if any, and returns the opposite truth value.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/complement"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "concat",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L488",
   :line 488,
   :var-type "function",
   :arglists ([] [x] [x y] [x y & zs]),
   :doc
   "Returns a lazy seq representing the concatenation of the elements in the supplied colls.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/concat"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "cond",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L400",
   :line 400,
   :var-type "macro",
   :arglists ([& clauses]),
   :doc
   "Takes a set of test/expr pairs. It evaluates each test one at a\ntime.  If a test returns logical true, cond evaluates and returns\nthe value of the corresponding expr and doesn't evaluate any of the\nother tests or exprs. (cond) returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/cond"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "condp",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4332",
   :line 4332,
   :var-type "macro",
   :arglists ([pred expr & clauses]),
   :doc
   "Takes a binary predicate, an expression, and a set of clauses.\nEach clause can take the form of either:\n\ntest-expr result-expr\n\ntest-expr :>> result-fn\n\nNote :>> is an ordinary keyword.\n\nFor each clause, (pred test-expr expr) is evaluated. If it returns\nlogical true, the clause is a match. If a binary clause matches, the\nresult-expr is returned, if a ternary clause matches, its result-fn,\nwhich must be a unary function, is called with the result of the\npredicate as its argument, the result of that call being the return\nvalue of condp. A single default expression can follow the clauses,\nand its value will be returned if no clause matches. If no default\nexpression is provided and no clause matches, an\nIllegalArgumentException is thrown.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/condp"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "conj",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L61",
   :line 61,
   :var-type "function",
   :arglists ([coll x] [coll x & xs]),
   :doc
   "conj[oin]. Returns a new collection with the xs\n'added'. (conj nil item) returns (item).  The 'addition' may\nhappen at different 'places' depending on the concrete type.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/conj"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "conj!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4617",
   :line 4617,
   :var-type "function",
   :arglists ([coll x]),
   :doc
   "Alpha - subject to change.\nAdds x to the transient collection, and return coll. The 'addition'\nmay happen at different 'places' depending on the concrete type.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/conj!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "cons",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L21",
   :line 21,
   :var-type "function",
   :arglists ([x seq]),
   :doc
   "Returns a new seq where x is the first element and seq is\nthe rest.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/cons"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "constantly",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L940",
   :line 940,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns a function that takes any number of arguments and returns x.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/constantly"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "construct-proxy",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L263",
   :line 263,
   :var-type "function",
   :arglists ([c & ctor-args]),
   :doc
   "Takes a proxy class and any arguments for its superclass ctor and\ncreates and returns an instance of the proxy.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/construct-proxy"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "contains?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L969",
   :line 969,
   :var-type "function",
   :arglists ([coll key]),
   :doc
   "Returns true if key is present in the given collection, otherwise\nreturns false.  Note that for numerically indexed collections like\nvectors and Java arrays, this tests if the numeric key is within the\nrange of indexes. 'contains?' operates constant or logarithmic time;\nit will not perform a linear search for a value.  See also 'some'.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/contains?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "count",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L606",
   :line 606,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns the number of items in the collection. (count nil) returns\n0.  Also works on strings, arrays, and Java Collections and Maps",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/count"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "counted?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4251",
   :line 4251,
   :var-type "function",
   :arglists ([coll]),
   :doc "Returns true if coll implements count in constant time",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/counted?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "create-ns",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2651",
   :line 2651,
   :var-type "function",
   :arglists ([sym]),
   :doc
   "Create a new namespace named by the symbol if one doesn't already\nexist, returns it or the already-existing namespace of the same\nname.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/create-ns"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "create-struct",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2587",
   :line 2587,
   :var-type "function",
   :arglists ([& keys]),
   :doc "Returns a structure basis object.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/create-struct"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "cycle",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1868",
   :line 1868,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns a lazy (infinite!) sequence of repetitions of the items in coll.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/cycle"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "dec",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L786",
   :line 786,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns a number one less than num.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/dec"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "decimal?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2278",
   :line 2278,
   :var-type "function",
   :arglists ([n]),
   :doc "Returns true if n is a BigDecimal",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/decimal?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "declare",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4275",
   :line 4275,
   :var-type "macro",
   :arglists ([& names]),
   :doc
   "defs the supplied var names with no bindings, useful for making forward declarations.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/declare"}
  {:raw-source-url nil,
   :added "1.0",
   :name "def",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Creates and interns a global var with the name\nof symbol in the current namespace (*ns*) or locates such a var if\nit already exists.  If init is supplied, it is evaluated, and the\nroot binding of the var is set to the resulting value.  If init is\nnot supplied, the root binding of the var is unaffected.\n\nPlease see http://clojure.org/special_forms#def",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/def",
   :forms [(def symbol doc-string? init?)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "definline",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3491",
   :line 3491,
   :var-type "macro",
   :arglists ([name & decl]),
   :doc
   "Experimental - like defmacro, except defines a named function whose\nbody is the expansion, calls to which may be expanded inline as if\nit were a macro. Cannot be used with variadic (&) args.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/definline"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "defmacro",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L311",
   :line 311,
   :var-type "macro",
   :arglists
   ([name doc-string? attr-map? [params*] body]
    [name doc-string? attr-map? ([params*] body) + attr-map?]),
   :doc
   "Like defn, but the resulting function name is declared as a\nmacro and will be used as a macro by the compiler when it is\ncalled.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/defmacro"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "defmethod",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1152",
   :line 1152,
   :var-type "macro",
   :arglists ([multifn dispatch-val & fn-tail]),
   :doc
   "Creates and installs a new method of multimethod associated with dispatch-value. ",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/defmethod"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "defmulti",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1113",
   :line 1113,
   :var-type "macro",
   :arglists ([name docstring? attr-map? dispatch-fn & options]),
   :doc
   "Creates a new multimethod with the associated dispatch function.\nThe docstring and attribute-map are optional.\n\nOptions are key-value pairs and may be one of:\n  :default    the default dispatch value, defaults to :default\n  :hierarchy  the isa? hierarchy to use for dispatching\n              defaults to the global hierarchy",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/defmulti"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "defn",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L206",
   :line 206,
   :var-type "macro",
   :arglists
   ([name doc-string? attr-map? [params*] body]
    [name doc-string? attr-map? ([params*] body) + attr-map?]),
   :doc
   "Same as (def name (fn [params* ] exprs*)) or (def\nname (fn ([params* ] exprs*)+)) with any doc-string or attrs added\nto the var metadata",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/defn"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "defn-",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3236",
   :line 3236,
   :var-type "macro",
   :arglists ([name & decls]),
   :doc "same as defn, yielding non-public def",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/defn-"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "defonce",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3932",
   :line 3932,
   :var-type "macro",
   :arglists ([name expr]),
   :doc
   "defs name to have the root value of the expr iff the named var has no root value,\nelse expr is unevaluated",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/defonce"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "defstruct",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2592",
   :line 2592,
   :var-type "macro",
   :arglists ([name & keys]),
   :doc "Same as (def name (create-struct keys...))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/defstruct"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "delay",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L516",
   :line 516,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "Takes a body of expressions and yields a Delay object that will\ninvoke the body only the first time it is forced (with force), and\nwill cache the result and return it on all subsequent force\ncalls.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/delay"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "delay?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L524",
   :line 524,
   :var-type "function",
   :arglists ([x]),
   :doc "returns true if x is a Delay created with delay",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/delay?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "deliver",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4596",
   :line 4596,
   :var-type "function",
   :arglists ([promise val]),
   :doc
   "Alpha - subject to change.\nDelivers the supplied value to the promise, releasing any pending\nderefs. A subsequent call to deliver on a promise will throw an exception.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/deliver"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "deref",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1444",
   :line 1444,
   :var-type "function",
   :arglists ([ref]),
   :doc
   "Also reader macro: @ref/@agent/@var/@atom/@delay/@future. Within a transaction,\nreturns the in-transaction-value of ref, else returns the\nmost-recently-committed value of ref. When applied to a var, agent\nor atom, returns its current state. When applied to a delay, forces\nit if not already forced. When applied to a future, will block if\ncomputation not complete",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/deref"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "derive",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3755",
   :line 3755,
   :var-type "function",
   :arglists ([tag parent] [h tag parent]),
   :doc
   "Establishes a parent/child relationship between parent and\ntag. Parent must be a namespace-qualified symbol or keyword and\nchild can be either a namespace-qualified symbol or keyword or a\nclass. h must be a hierarchy obtained from make-hierarchy, if not\nsupplied defaults to, and modifies, the global hierarchy.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/derive"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "descendants",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3744",
   :line 3744,
   :var-type "function",
   :arglists ([tag] [h tag]),
   :doc
   "Returns the immediate and indirect children of tag, through a\nrelationship established via derive. h must be a hierarchy obtained\nfrom make-hierarchy, if not supplied defaults to the global\nhierarchy. Note: does not work on Java type inheritance\nrelationships.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/descendants"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "disj",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L996",
   :line 996,
   :var-type "function",
   :arglists ([set] [set key] [set key & ks]),
   :doc
   "disj[oin]. Returns a new set of the same (hashed/sorted) type, that\ndoes not contain key(s).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/disj"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "disj!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4653",
   :line 4653,
   :var-type "function",
   :arglists ([set] [set key] [set key & ks]),
   :doc
   "Alpha - subject to change.\ndisj[oin]. Returns a transient set of the same (hashed/sorted) type, that\ndoes not contain key(s).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/disj!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "dissoc",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L984",
   :line 984,
   :var-type "function",
   :arglists ([map] [map key] [map key & ks]),
   :doc
   "dissoc[iate]. Returns a new map of the same (hashed/sorted) type,\nthat does not contain a mapping for key(s).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/dissoc"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "dissoc!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4636",
   :line 4636,
   :var-type "function",
   :arglists ([map key] [map key & ks]),
   :doc
   "Alpha - subject to change.\nReturns a transient map that doesn't contain a mapping for key(s).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/dissoc!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "distinct",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3378",
   :line 3378,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns a lazy sequence of the elements of coll with duplicates removed",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/distinct"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "distinct?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3812",
   :line 3812,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc "Returns true if no two of the arguments are =",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/distinct?"}
  {:raw-source-url nil,
   :added "1.0",
   :name "do",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Evaluates the expressions in order and returns the value of\nthe last. If no expressions are supplied, returns nil.\n\nPlease see http://clojure.org/special_forms#do",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/do",
   :forms [(do exprs*)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "doall",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2089",
   :line 2089,
   :var-type "function",
   :arglists ([coll] [n coll]),
   :doc
   "When lazy sequences are produced via functions that have side\neffects, any effects other than those needed to produce the first\nelement in the seq do not occur until the seq is consumed. doall can\nbe used to force any effects. Walks through the successive nexts of\nthe seq, retains the head and returns it, thus causing the entire\nseq to reside in memory at one time.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/doall"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "doc",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3288",
   :line 3288,
   :var-type "macro",
   :arglists ([name]),
   :doc
   "Prints documentation for a var or special form given its name",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/doc"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "dorun",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2076",
   :line 2076,
   :var-type "function",
   :arglists ([coll] [n coll]),
   :doc
   "When lazy sequences are produced via functions that have side\neffects, any effects other than those needed to produce the first\nelement in the seq do not occur until the seq is consumed. dorun can\nbe used to force any effects. Walks through the successive nexts of\nthe seq, does not retain the head and returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/dorun"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "doseq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2019",
   :line 2019,
   :var-type "macro",
   :arglists ([seq-exprs & body]),
   :doc
   "Repeatedly executes body (presumably for side-effects) with\nbindings and filtering as provided by \"for\".  Does not retain\nthe head of the sequence. Returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/doseq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "dosync",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3406",
   :line 3406,
   :var-type "macro",
   :arglists ([& exprs]),
   :doc
   "Runs the exprs (in an implicit do) in a transaction that encompasses\nexprs and any nested calls.  Starts a transaction if none is already\nrunning on this thread. Any uncaught exception will abort the\ntransaction and flow out of dosync. The exprs may be run more than\nonce, but any effects on Refs will be atomic.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/dosync"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "dotimes",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2137",
   :line 2137,
   :var-type "macro",
   :arglists ([bindings & body]),
   :doc
   "bindings => name n\n\nRepeatedly executes body (presumably for side-effects) with name\nbound to integers from 0 through n-1.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/dotimes"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "doto",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2429",
   :line 2429,
   :var-type "macro",
   :arglists ([x & forms]),
   :doc
   "Evaluates x then calls all of the methods and functions with the\nvalue of x supplied at the from of the given arguments.  The forms\nare evaluated in order.  Returns x.\n\n(doto (new java.util.HashMap) (.put \"a\" 1) (.put \"b\" 2))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/doto"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "double",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2222",
   :line 2222,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to double",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/double"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "double-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3569",
   :line 3569,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of doubles",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/double-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "doubles",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3614",
   :line 3614,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to double[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/doubles"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "drop",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1833",
   :line 1833,
   :var-type "function",
   :arglists ([n coll]),
   :doc
   "Returns a lazy sequence of all but the first n items in coll.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/drop"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "drop-last",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1843",
   :line 1843,
   :var-type "function",
   :arglists ([s] [n s]),
   :doc
   "Return a lazy sequence of all but the last n (default 1) items in coll",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/drop-last"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "drop-while",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1857",
   :line 1857,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns a lazy sequence of the items in coll starting from the first\nitem for which (pred item) returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/drop-while"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "empty",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3502",
   :line 3502,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns an empty collection of the same category as coll, or nil",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/empty"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "empty?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4212",
   :line 4212,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns true if coll has no items - same as (not (seq coll)).\nPlease use the idiom (seq x) rather than (not (empty? x))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/empty?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ensure",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1569",
   :line 1569,
   :var-type "function",
   :arglists ([ref]),
   :doc
   "Must be called in a transaction. Protects the ref from modification\nby other transactions.  Returns the in-transaction-value of\nref. Allows for more concurrency than (ref-set ref @ref)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ensure"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "enumeration-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3851",
   :line 3851,
   :var-type "function",
   :arglists ([e]),
   :doc "Returns a seq on a java.util.Enumeration",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/enumeration-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "eval",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2015",
   :line 2015,
   :var-type "function",
   :arglists ([form]),
   :doc
   "Evaluates the form data structure (not text!) and returns the result.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/eval"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "even?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L919",
   :line 919,
   :var-type "function",
   :arglists ([n]),
   :doc
   "Returns true if n is even, throws an exception if n is not an integer",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/even?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "every?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1698",
   :line 1698,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns true if (pred x) is logical true for every x in coll, else\nfalse.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/every?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "false?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L341",
   :line 341,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is the value false, false otherwise.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/false?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ffirst",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L78",
   :line 78,
   :var-type "function",
   :arglists ([x]),
   :doc "Same as (first (first x))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ffirst"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "file-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3317",
   :line 3317,
   :var-type "function",
   :arglists ([dir]),
   :doc "A tree seq on java.io.Files",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/file-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "filter",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1789",
   :line 1789,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns a lazy sequence of the items in coll for which\n(pred item) returns true. pred must be free of side-effects.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/filter"}
  {:raw-source-url nil,
   :added "1.0",
   :name "finally",
   :file nil,
   :source-url nil,
   :var-type "special syntax",
   :arglists nil,
   :doc
   "Syntax for use with try.\n\nPlease see http://clojure.org/special_forms#try",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/finally"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "find",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1008",
   :line 1008,
   :var-type "function",
   :arglists ([map key]),
   :doc "Returns the map entry for key, or nil if key not present.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/find"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "find-doc",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3249",
   :line 3249,
   :var-type "function",
   :arglists ([re-string-or-pattern]),
   :doc
   "Prints documentation for any var whose documentation or name\ncontains a match for re-string-or-pattern",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/find-doc"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "find-ns",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2647",
   :line 2647,
   :var-type "function",
   :arglists ([sym]),
   :doc
   "Returns the namespace named by the symbol or nil if it doesn't exist.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/find-ns"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "find-var",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1312",
   :line 1312,
   :var-type "function",
   :arglists ([sym]),
   :doc
   "Returns the global var named by the namespace-qualified symbol, or\nnil if no var with that name.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/find-var"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "first",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L41",
   :line 41,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns the first item in the collection. Calls seq on its\nargument. If coll is nil, returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/first"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "float",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2216",
   :line 2216,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to float",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/float"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "float-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3534",
   :line 3534,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of floats",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/float-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "float?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2282",
   :line 2282,
   :var-type "function",
   :arglists ([n]),
   :doc "Returns true if n is a floating point number",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/float?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "floats",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3606",
   :line 3606,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to float[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/floats"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "flush",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2345",
   :line 2345,
   :var-type "function",
   :arglists ([]),
   :doc
   "Flushes the output stream that is the current value of\n*out*",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/flush"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "fn",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2913",
   :line 2913,
   :var-type "macro",
   :arglists ([& sigs]),
   :doc
   "(fn name? [params* ] exprs*)\n(fn name? ([params* ] exprs*)+)\n\nparams => positional-params* , or positional-params* & next-param\npositional-param => binding-form\nnext-param => binding-form\nname => symbol\n\nDefines a function",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/fn"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "fn?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4234",
   :line 4234,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns true if x implements Fn, i.e. is an object created via fn.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/fn?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "fnext",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L88",
   :line 88,
   :var-type "function",
   :arglists ([x]),
   :doc "Same as (first (next x))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/fnext"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "for",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3013",
   :line 3013,
   :var-type "macro",
   :arglists ([seq-exprs body-expr]),
   :doc
   "List comprehension. Takes a vector of one or more\n binding-form/collection-expr pairs, each followed by zero or more\n modifiers, and yields a lazy sequence of evaluations of expr.\n Collections are iterated in a nested fashion, rightmost fastest,\n and nested coll-exprs can refer to bindings created in prior\n binding-forms.  Supported modifiers are: :let [binding-form expr ...],\n :while test, :when test.\n\n(take 100 (for [x (range 100000000) y (range 1000000) :while (< y x)] [x y]))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/for"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "force",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L528",
   :line 528,
   :var-type "function",
   :arglists ([x]),
   :doc
   "If x is a Delay, returns the (possibly cached) value of its expression, else returns x",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/force"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "format",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3856",
   :line 3856,
   :var-type "function",
   :arglists ([fmt & args]),
   :doc
   "Formats a string using java.lang.String.format, see java.util.Formatter for format\nstring syntax",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/format"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "future",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4481",
   :line 4481,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "Takes a body of expressions and yields a future object that will\ninvoke the body in another thread, and will cache the result and\nreturn it on all subsequent calls to deref/@. If the computation has\nnot yet finished, calls to deref/@ will block.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/future"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "future-call",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4466",
   :line 4466,
   :var-type "function",
   :arglists ([f]),
   :doc
   "Takes a function of no args and yields a future object that will\ninvoke the function in another thread, and will cache the result and\nreturn it on all subsequent calls to deref/@. If the computation has\nnot yet finished, calls to deref/@ will block.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/future-call"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "future-cancel",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4489",
   :line 4489,
   :var-type "function",
   :arglists ([f]),
   :doc "Cancels the future, if possible.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/future-cancel"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "future-cancelled?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4493",
   :line 4493,
   :var-type "function",
   :arglists ([f]),
   :doc "Returns true if future f is cancelled",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/future-cancelled?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "future-done?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4455",
   :line 4455,
   :var-type "function",
   :arglists ([f]),
   :doc "Returns true if future f is done",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/future-done?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "future?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4451",
   :line 4451,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is a future",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/future?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/6109d41a975bf24b17681342591116a9897e4a27/src/clj/clojure/genclass.clj",
   :name "gen-class",
   :file "src/clj/clojure/genclass.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/6109d41a975bf24b17681342591116a9897e4a27/src/clj/clojure/genclass.clj#L464",
   :line 464,
   :var-type "macro",
   :arglists ([& options]),
   :doc
   "When compiling, generates compiled bytecode for a class with the\ngiven package-qualified :name (which, as all names in these\nparameters, can be a string or symbol), and writes the .class file\nto the *compile-path* directory.  When not compiling, does\nnothing. The gen-class construct contains no implementation, as the\nimplementation will be dynamically sought by the generated class in\nfunctions in an implementing Clojure namespace. Given a generated\nclass org.mydomain.MyClass with a method named mymethod, gen-class\nwill generate an implementation that looks for a function named by \n(str prefix mymethod) (default prefix: \"-\") in a\nClojure namespace specified by :impl-ns\n(defaults to the current namespace). All inherited methods,\ngenerated methods, and init and main functions (see :methods, :init,\nand :main below) will be found similarly prefixed. By default, the\nstatic initializer for the generated class will attempt to load the\nClojure support code for the class as a resource from the classpath,\ne.g. in the example case, ``org/mydomain/MyClass__init.class``. This\nbehavior can be controlled by :load-impl-ns\n\nNote that methods with a maximum of 18 parameters are supported.\n\nIn all subsequent sections taking types, the primitive types can be\nreferred to by their Java names (int, float etc), and classes in the\njava.lang package can be used without a package qualifier. All other\nclasses must be fully qualified.\n\nOptions should be a set of key/value pairs, all except for :name are optional:\n\n:name aname\n\nThe package-qualified name of the class to be generated\n\n:extends aclass\n\nSpecifies the superclass, the non-private methods of which will be\noverridden by the class. If not provided, defaults to Object.\n\n:implements [interface ...]\n\nOne or more interfaces, the methods of which will be implemented by the class.\n\n:init name\n\nIf supplied, names a function that will be called with the arguments\nto the constructor. Must return [ [superclass-constructor-args] state] \nIf not supplied, the constructor args are passed directly to\nthe superclass constructor and the state will be nil\n\n:constructors {[param-types] [super-param-types], ...}\n\nBy default, constructors are created for the generated class which\nmatch the signature(s) of the constructors for the superclass. This\nparameter may be used to explicitly specify constructors, each entry\nproviding a mapping from a constructor signature to a superclass\nconstructor signature. When you supply this, you must supply an :init\nspecifier. \n\n:post-init name\n\nIf supplied, names a function that will be called with the object as\nthe first argument, followed by the arguments to the constructor.\nIt will be called every time an object of this class is created,\nimmediately after all the inherited constructors have completed.\nIt's return value is ignored.\n\n:methods [ [name [param-types] return-type], ...]\n\nThe generated class automatically defines all of the non-private\nmethods of its superclasses/interfaces. This parameter can be used\nto specify the signatures of additional methods of the generated\nclass. Static methods can be specified with #^{:static true} in the\nsignature's metadata. Do not repeat superclass/interface signatures\nhere.\n\n:main boolean\n\nIf supplied and true, a static public main function will be generated. It will\npass each string of the String[] argument as a separate argument to\na function called (str prefix main).\n\n:factory name\n\nIf supplied, a (set of) public static factory function(s) will be\ncreated with the given name, and the same signature(s) as the\nconstructor(s).\n\n:state name\n\nIf supplied, a public final instance field with the given name will be\ncreated. You must supply an :init function in order to provide a\nvalue for the state. Note that, though final, the state can be a ref\nor agent, supporting the creation of Java objects with transactional\nor asynchronous mutation semantics.\n\n:exposes {protected-field-name {:get name :set name}, ...}\n\nSince the implementations of the methods of the generated class\noccur in Clojure functions, they have no access to the inherited\nprotected fields of the superclass. This parameter can be used to\ngenerate public getter/setter methods exposing the protected field(s)\nfor use in the implementation.\n\n:exposes-methods {super-method-name exposed-name, ...}\n\nIt is sometimes necessary to call the superclass' implementation of an\noverridden method.  Those methods may be exposed and referred in \nthe new method implementation by a local name.\n\n:prefix string\n\nDefault: \"-\" Methods called e.g. Foo will be looked up in vars called\nprefixFoo in the implementing ns.\n\n:impl-ns name\n\nDefault: the name of the current ns. Implementations of methods will be \nlooked up in this namespace.\n\n:load-impl-ns boolean\n\nDefault: true. Causes the static initializer for the generated class\nto reference the load code for the implementing namespace. Should be\ntrue when implementing-ns is the default, false if you intend to\nload the code via some other method.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/gen-class"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/6109d41a975bf24b17681342591116a9897e4a27/src/clj/clojure/genclass.clj",
   :name "gen-interface",
   :file "src/clj/clojure/genclass.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/6109d41a975bf24b17681342591116a9897e4a27/src/clj/clojure/genclass.clj#L635",
   :line 635,
   :var-type "macro",
   :arglists ([& options]),
   :doc
   "When compiling, generates compiled bytecode for an interface with\n the given package-qualified :name (which, as all names in these\n parameters, can be a string or symbol), and writes the .class file\n to the *compile-path* directory.  When not compiling, does nothing.\n\n In all subsequent sections taking types, the primitive types can be\n referred to by their Java names (int, float etc), and classes in the\n java.lang package can be used without a package qualifier. All other\n classes must be fully qualified.\n\n Options should be a set of key/value pairs, all except for :name are\n optional:\n\n :name aname\n\n The package-qualified name of the class to be generated\n\n :extends [interface ...]\n\n One or more interfaces, which will be extended by this interface.\n\n :methods [ [name [param-types] return-type], ...]\n\n This parameter is used to specify the signatures of the methods of\n the generated interface.  Do not repeat superinterface signatures\n here.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/gen-interface"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "gensym",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L393",
   :line 393,
   :var-type "function",
   :arglists ([] [prefix-string]),
   :doc
   "Returns a new symbol with a unique name. If a prefix string is\nsupplied, the name is prefix# where # is some unique number. If\nprefix is not supplied, the prefix is 'G__'.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/gensym"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "get",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L977",
   :line 977,
   :var-type "function",
   :arglists ([map key] [map key not-found]),
   :doc
   "Returns the value mapped to key, not-found or nil if key not present.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/get"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "get-in",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4186",
   :line 4186,
   :var-type "function",
   :arglists ([m ks]),
   :doc
   "returns the value in a nested associative structure, where ks is a sequence of keys",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/get-in"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "get-method",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1172",
   :line 1172,
   :var-type "function",
   :arglists ([multifn dispatch-val]),
   :doc
   "Given a multimethod and a dispatch value, returns the dispatch fn\nthat would apply to that value, or nil if none apply and no default",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/get-method"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "get-proxy-class",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L250",
   :line 250,
   :var-type "function",
   :arglists ([& bases]),
   :doc
   "Takes an optional single class followed by zero or more\ninterfaces. If not supplied class defaults to Object.  Creates an\nreturns an instance of a proxy class derived from the supplied\nclasses. The resulting value is cached and used for any subsequent\nrequests for the same class set. Returns a Class object.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/get-proxy-class"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "get-thread-bindings",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1245",
   :line 1245,
   :var-type "function",
   :arglists ([]),
   :doc
   "Get a map with the Var/value pairs which is currently in effect for the\ncurrent thread.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/get-thread-bindings"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "get-validator",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1499",
   :line 1499,
   :var-type "function",
   :arglists ([iref]),
   :doc "Gets the validator-fn for a var/ref/agent/atom.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/get-validator"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "hash",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3483",
   :line 3483,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns the hash code of its argument",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/hash"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "hash-map",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L274",
   :line 274,
   :var-type "function",
   :arglists ([] [& keyvals]),
   :doc
   "keyval => key val\nReturns a new hash map with supplied mappings.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/hash-map"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "hash-set",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L281",
   :line 281,
   :var-type "function",
   :arglists ([] [& keys]),
   :doc "Returns a new hash set with supplied keys.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/hash-set"}
  {:name "identical?",
   :doc "Tests if 2 arguments are the same object",
   :var-type "function",
   :namespace "clojure.core",
   :arglists ([x y]),
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/identical?",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "identity",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L944",
   :line 944,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns its argument.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/identity"}
  {:raw-source-url nil,
   :added "1.0",
   :name "if",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Evaluates test. If not the singular values nil or false,\nevaluates and yields then, otherwise, evaluates and yields else. If\nelse is not supplied it defaults to nil.\n\nPlease see http://clojure.org/special_forms#if",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/if",
   :forms [(if test then else?)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "if-let",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1191",
   :line 1191,
   :var-type "macro",
   :arglists ([bindings then] [bindings then else & oldform]),
   :doc
   "bindings => binding-form test\n\nIf test is true, evaluates then with binding-form bound to the value of \ntest, if not, yields else",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/if-let"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "if-not",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L532",
   :line 532,
   :var-type "macro",
   :arglists ([test then] [test then else]),
   :doc
   "Evaluates test. If logical false, evaluates and returns then expr, \notherwise else expr, if supplied, else nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/if-not"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ifn?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4229",
   :line 4229,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns true if x implements IFn. Note that many data structures\n(e.g. sets and maps) implement IFn",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ifn?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "import",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2163",
   :line 2163,
   :var-type "macro",
   :arglists ([& import-symbols-or-lists]),
   :doc
   "import-list => (package-symbol class-name-symbols*)\n\nFor each name in class-name-symbols, adds a mapping from name to the\nclass named by package.name to the current namespace. Use :import in the ns\nmacro in preference to calling this directly.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/import"}
  {:name "in-ns",
   :doc
   "Sets *ns* to the namespace named by the symbol, creating it if needed.",
   :var-type "function",
   :namespace "clojure.core",
   :arglists ([name]),
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/in-ns",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "inc",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L641",
   :line 641,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns a number one greater than num.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/inc"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "init-proxy",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L269",
   :line 269,
   :var-type "function",
   :arglists ([proxy mappings]),
   :doc
   "Takes a proxy instance and a map of strings (which must\ncorrespond to methods of the proxy superclass/superinterfaces) to\nfns (which must take arguments matching the corresponding method,\nplus an additional (explicit) first arg corresponding to this, and\nsets the proxy's fn map.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/init-proxy"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "instance?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L107",
   :line 107,
   :var-type "function",
   :arglists ([c x]),
   :doc
   "Evaluates x and tests if it is an instance of the class\nc. Returns true or false",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/instance?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "int",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L611",
   :line 611,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to int",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/int"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "int-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3576",
   :line 3576,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of ints",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/int-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "integer?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2257",
   :line 2257,
   :var-type "function",
   :arglists ([n]),
   :doc "Returns true if n is an integer",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/integer?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "interleave",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2778",
   :line 2778,
   :var-type "function",
   :arglists ([c1 c2] [c1 c2 & colls]),
   :doc
   "Returns a lazy seq of the first item in each coll, then the second etc.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/interleave"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "intern",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4295",
   :line 4295,
   :var-type "function",
   :arglists ([ns name] [ns name val]),
   :doc
   "Finds or creates a var named by the symbol name in the namespace\nns (which can be a symbol or a namespace), setting its root binding\nto val if supplied. The namespace must exist. The var will adopt any\nmetadata from the name symbol.  Returns the var.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/intern"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "interpose",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3487",
   :line 3487,
   :var-type "function",
   :arglists ([sep coll]),
   :doc "Returns a lazy seq of the elements of coll separated by sep",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/interpose"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "into",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4667",
   :line 4667,
   :var-type "function",
   :arglists ([to from]),
   :doc
   "Returns a new coll consisting of to-coll with all of the items of\nfrom-coll conjoined.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/into"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "into-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2180",
   :line 2180,
   :var-type "function",
   :arglists ([aseq] [type aseq]),
   :doc
   "Returns an array with components set to the values in aseq. The array's\ncomponent type is type if provided, or the type of the first value in\naseq if present, or Object. All values in aseq must be compatible with\nthe component type. Class objects for the primitive types can be obtained\nusing, e.g., Integer/TYPE.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/into-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ints",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3610",
   :line 3610,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to int[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ints"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "io!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1590",
   :line 1590,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "If an io! block occurs in a transaction, throws an\nIllegalStateException, else runs body in an implicit do. If the\nfirst expression in body is a literal string, will use that as the\nexception message.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/io!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "isa?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3697",
   :line 3697,
   :var-type "function",
   :arglists ([child parent] [h child parent]),
   :doc
   "Returns true if (= child parent), or child is directly or indirectly derived from\nparent, either via a Java type inheritance relationship or a\nrelationship established via derive. h must be a hierarchy obtained\nfrom make-hierarchy, if not supplied defaults to the global\nhierarchy",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/isa?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "iterate",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1893",
   :line 1893,
   :var-type "function",
   :arglists ([f x]),
   :doc
   "Returns a lazy sequence of x, (f x), (f (f x)) etc. f must be free of side-effects",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/iterate"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "iterator-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3845",
   :line 3845,
   :var-type "function",
   :arglists ([iter]),
   :doc
   "Returns a seq on a java.util.Iterator. Note that most collections\nproviding iterators implement Iterable and thus support seq directly.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/iterator-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "juxt",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1633",
   :line 1633,
   :var-type "function",
   :arglists ([f] [f g] [f g h] [f g h & fs]),
   :doc
   "Alpha - name subject to change.\nTakes a set of functions and returns a fn that is the juxtaposition\nof those fns.  The returned fn takes a variable number of args, and\nreturns a vector containing the result of applying each fn to the\nargs (left-to-right).\n((juxt a b c) x) => [(a x) (b x) (c x)]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/juxt"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "key",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1033",
   :line 1033,
   :var-type "function",
   :arglists ([e]),
   :doc "Returns the key of the map entry.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/key"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "keys",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1025",
   :line 1025,
   :var-type "function",
   :arglists ([map]),
   :doc "Returns a sequence of the map's keys.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/keys"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "keyword",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L386",
   :line 386,
   :var-type "function",
   :arglists ([name] [ns name]),
   :doc
   "Returns a Keyword with the given namespace and name.  Do not use :\nin the keyword strings, it will be added automatically.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/keyword"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "keyword?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L376",
   :line 376,
   :var-type "function",
   :arglists ([x]),
   :doc "Return true if x is a Keyword",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/keyword?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "last",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L189",
   :line 189,
   :var-type "function",
   :arglists ([coll]),
   :doc "Return the last item in coll, in linear time",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/last"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "lazy-cat",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3004",
   :line 3004,
   :var-type "macro",
   :arglists ([& colls]),
   :doc
   "Expands to code which yields a lazy sequence of the concatenation\nof the supplied colls.  Each coll expr is not evaluated until it is\nneeded. \n\n(lazy-cat xs ys zs) === (concat (lazy-seq xs) (lazy-seq ys) (lazy-seq zs))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/lazy-cat"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "lazy-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L454",
   :line 454,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "Takes a body of expressions that returns an ISeq or nil, and yields\na Seqable object that will invoke the body only the first time seq\nis called, and will cache the result and return it on all subsequent\nseq calls.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/lazy-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "let",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2902",
   :line 2902,
   :var-type "macro",
   :arglists ([bindings & body]),
   :doc
   "Evaluates the exprs in a lexical context in which the symbols in\nthe binding-forms are bound to their respective init-exprs or parts\ntherein.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/let"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "letfn",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4531",
   :line 4531,
   :var-type "macro",
   :arglists ([fnspecs & body]),
   :doc
   "Takes a vector of function specs and a body, and generates a set of\nbindings of functions to their names. All of the names are available\nin all of the definitions of the functions, as well as the body.\n\nfnspec ==> (fname [params*] exprs) or (fname ([params*] exprs)+)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/letfn"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "line-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1954",
   :line 1954,
   :var-type "function",
   :arglists ([rdr]),
   :doc
   "Returns the lines of text from rdr as a lazy sequence of strings.\nrdr must implement java.io.BufferedReader.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/line-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "list",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L16",
   :line 16,
   :var-type "function",
   :arglists ([& items]),
   :doc "Creates a new list containing the items.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/list"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "list*",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L422",
   :line 422,
   :var-type "function",
   :arglists
   ([args] [a args] [a b args] [a b c args] [a b c d & more]),
   :doc
   "Creates a new list containing the items prepended to the rest, the\nlast of which will be treated as a sequence.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/list*"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "list?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4221",
   :line 4221,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x implements IPersistentList",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/list?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "load",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4154",
   :line 4154,
   :var-type "function",
   :arglists ([& paths]),
   :doc
   "Loads Clojure code from resources in classpath. A path is interpreted as\nclasspath-relative if it begins with a slash or relative to the root\ndirectory for the current namespace otherwise.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/load"}
  {:name "load-file",
   :doc
   "Sequentially read and evaluate the set of forms contained in the file.",
   :var-type "function",
   :namespace "clojure.core",
   :arglists ([name]),
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/load-file",
   :source-url nil,
   :raw-source-url nil,
   :file nil}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "load-reader",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2621",
   :line 2621,
   :var-type "function",
   :arglists ([rdr]),
   :doc
   "Sequentially read and evaluate the set of forms contained in the\nstream/file",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/load-reader"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "load-string",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2626",
   :line 2626,
   :var-type "function",
   :arglists ([s]),
   :doc
   "Sequentially read and evaluate the set of forms contained in the\nstring",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/load-string"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "loaded-libs",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4150",
   :line 4150,
   :var-type "function",
   :arglists ([]),
   :doc
   "Returns a sorted set of symbols naming the currently loaded libs",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/loaded-libs"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "locking",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1061",
   :line 1061,
   :var-type "macro",
   :arglists ([x & body]),
   :doc
   "Executes exprs in an implicit do, while holding the monitor of x.\nWill release the monitor of x in all circumstances.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/locking"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "long",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2210",
   :line 2210,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to long",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/long"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "long-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3583",
   :line 3583,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of longs",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/long-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "longs",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3618",
   :line 3618,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to long[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/longs"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "loop",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2967",
   :line 2967,
   :var-type "macro",
   :arglists ([bindings & body]),
   :doc
   "Evaluates the exprs in a lexical context in which the symbols in\nthe binding-forms are bound to their respective init-exprs or parts\ntherein. Acts as a recur target.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/loop"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "macroexpand",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2577",
   :line 2577,
   :var-type "function",
   :arglists ([form]),
   :doc
   "Repeatedly calls macroexpand-1 on form until it no longer\nrepresents a macro form, then returns it.  Note neither\nmacroexpand-1 nor macroexpand expand macros in subforms.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/macroexpand"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "macroexpand-1",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2571",
   :line 2571,
   :var-type "function",
   :arglists ([form]),
   :doc
   "If form represents a macro form, returns its expansion,\nelse returns form.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/macroexpand-1"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "make-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2543",
   :line 2543,
   :var-type "function",
   :arglists ([type len] [type dim & more-dims]),
   :doc
   "Creates and returns an array of instances of the specified class of\nthe specified dimension(s).  Note that a class object is required.\nClass objects can be obtained by using their imported or\nfully-qualified name.  Class objects for the primitive types can be\nobtained using, e.g., Integer/TYPE.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/make-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "make-hierarchy",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3669",
   :line 3669,
   :var-type "function",
   :arglists ([]),
   :doc "Creates a hierarchy object for use with derive, isa? etc.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/make-hierarchy"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "map",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1746",
   :line 1746,
   :var-type "function",
   :arglists ([f coll] [f c1 c2] [f c1 c2 c3] [f c1 c2 c3 & colls]),
   :doc
   "Returns a lazy sequence consisting of the result of applying f to the\nset of first items of each coll, followed by applying f to the set\nof second items in each coll, until any one of the colls is\nexhausted.  Any remaining items in other colls are ignored. Function\nf should accept number-of-colls arguments.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/map"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "map?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L128",
   :line 128,
   :var-type "function",
   :arglists ([x]),
   :doc "Return true if x implements IPersistentMap",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/map?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "mapcat",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1783",
   :line 1783,
   :var-type "function",
   :arglists ([f & colls]),
   :doc
   "Returns the result of applying concat to the result of applying map\nto f and colls.  Thus function f should return a collection.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/mapcat"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "max",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L772",
   :line 772,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc "Returns the greatest of the nums.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/max"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "max-key",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3364",
   :line 3364,
   :var-type "function",
   :arglists ([k x] [k x y] [k x y & more]),
   :doc "Returns the x for which (k x), a number, is greatest.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/max-key"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "memfn",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2445",
   :line 2445,
   :var-type "macro",
   :arglists ([name & args]),
   :doc
   "Expands into code that creates a fn that expects to be passed an\nobject and any args and calls the named instance method on the\nobject passing the args. Use when you want to treat a Java method as\na first-class fn.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/memfn"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "memoize",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4318",
   :line 4318,
   :var-type "function",
   :arglists ([f]),
   :doc
   "Returns a memoized version of a referentially transparent function. The\nmemoized version of the function keeps a cache of the mapping from arguments\nto results and, when calls with the same arguments are repeated often, has\nhigher performance at the expense of higher memory use.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/memoize"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "merge",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1916",
   :line 1916,
   :var-type "function",
   :arglists ([& maps]),
   :doc
   "Returns a map that consists of the rest of the maps conj-ed onto\nthe first.  If a key occurs in more than one map, the mapping from\nthe latter (left-to-right) will be the mapping in the result.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/merge"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "merge-with",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1924",
   :line 1924,
   :var-type "function",
   :arglists ([f & maps]),
   :doc
   "Returns a map that consists of the rest of the maps conj-ed onto\nthe first.  If a key occurs in more than one map, the mapping(s)\nfrom the latter (left-to-right) will be combined with the mapping in\nthe result by calling (f val-in-result val-in-latter).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/merge-with"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "meta",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L154",
   :line 154,
   :var-type "function",
   :arglists ([obj]),
   :doc
   "Returns the metadata of obj, returns nil if there is no metadata.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/meta"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "methods",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1168",
   :line 1168,
   :var-type "function",
   :arglists ([multifn]),
   :doc
   "Given a multimethod, returns a map of dispatch values -> dispatch fns",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/methods"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "min",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L779",
   :line 779,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc "Returns the least of the nums.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/min"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "min-key",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3371",
   :line 3371,
   :var-type "function",
   :arglists ([k x] [k x y] [k x y & more]),
   :doc "Returns the x for which (k x), a number, is least.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/min-key"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "mod",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2266",
   :line 2266,
   :var-type "function",
   :arglists ([num div]),
   :doc "Modulus of num and div. Truncates toward negative infinity.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/mod"}
  {:raw-source-url nil,
   :added "1.0",
   :name "monitor-enter",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Synchronization primitive that should be avoided\nin user code. Use the 'locking' macro.\n\nPlease see http://clojure.org/special_forms#monitor-enter",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/monitor-enter",
   :forms [(monitor-enter x)]}
  {:raw-source-url nil,
   :added "1.0",
   :name "monitor-exit",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Synchronization primitive that should be avoided\nin user code. Use the 'locking' macro.\n\nPlease see http://clojure.org/special_forms#monitor-exit",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/monitor-exit",
   :forms [(monitor-exit x)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "name",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1049",
   :line 1049,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns the name String of a symbol or keyword.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/name"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "namespace",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1055",
   :line 1055,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns the namespace String of a symbol or keyword, or nil if not present.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/namespace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "neg?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L845",
   :line 845,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if num is less than zero, else false",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/neg?"}
  {:raw-source-url nil,
   :added "1.0",
   :name "new",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "The args, if any, are evaluated from left to right, and\npassed to the constructor of the class named by Classname. The\nconstructed object is returned.\n\nPlease see http://clojure.org/java_interop#new",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/new",
   :forms [(Classname. args*) (new Classname args*)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "newline",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2338",
   :line 2338,
   :var-type "function",
   :arglists ([]),
   :doc
   "Writes a newline to the output stream that is the current value of\n*out*",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/newline"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "next",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L47",
   :line 47,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns a seq of the items after the first. Calls seq on its\nargument.  If there are no more items, returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/next"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "nfirst",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L83",
   :line 83,
   :var-type "function",
   :arglists ([x]),
   :doc "Same as (next (first x))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/nfirst"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "nil?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L336",
   :line 336,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is nil, false otherwise.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/nil?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "nnext",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L93",
   :line 93,
   :var-type "function",
   :arglists ([x]),
   :doc "Same as (next (next x))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/nnext"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "not",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L351",
   :line 351,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is logical false, false otherwise.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/not"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "not-any?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1724",
   :line 1724,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns false if (pred x) is logical true for any x in coll,\nelse true.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/not-any?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "not-empty",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3676",
   :line 3676,
   :var-type "function",
   :arglists ([coll]),
   :doc "If coll is empty, returns nil, else coll",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/not-empty"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "not-every?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1708",
   :line 1708,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns false if (pred x) is logical true for every x in\ncoll, else true.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/not-every?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "not=",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L557",
   :line 557,
   :var-type "function",
   :arglists ([x] [x y] [x y & more]),
   :doc "Same as (not (= obj1 obj2))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/not="}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3879",
   :line 3879,
   :var-type "macro",
   :arglists ([name & references]),
   :doc
   "Sets *ns* to the namespace named by name (unevaluated), creating it\nif needed.  references can be zero or more of: (:refer-clojure ...)\n(:require ...) (:use ...) (:import ...) (:load ...) (:gen-class)\nwith the syntax of refer-clojure/require/use/import/load/gen-class\nrespectively, except the arguments are unevaluated and need not be\nquoted. (:gen-class ...), when supplied, defaults to :name\ncorresponding to the ns name, :main true, :impl-ns same as ns, and\n:init-impl-ns true. All options of gen-class are\nsupported. The :gen-class directive is ignored when not\ncompiling. If :gen-class is not supplied, when compiled only an\nnsname__init.class will be generated. If :refer-clojure is not used, a\ndefault (refer 'clojure) is used.  Use of ns is preferred to\nindividual calls to in-ns/require/use/import:\n\n(ns foo.bar\n  (:refer-clojure :exclude [ancestors printf])\n  (:require (clojure.contrib sql sql.tests))\n  (:use (my.lib this that))\n  (:import (java.util Date Timer Random)\n            (java.sql Connection Statement)))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-aliases",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2761",
   :line 2761,
   :var-type "function",
   :arglists ([ns]),
   :doc "Returns a map of the aliases for the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-aliases"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-imports",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2703",
   :line 2703,
   :var-type "function",
   :arglists ([ns]),
   :doc "Returns a map of the import mappings for the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-imports"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-interns",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2745",
   :line 2745,
   :var-type "function",
   :arglists ([ns]),
   :doc "Returns a map of the intern mappings for the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-interns"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-map",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2680",
   :line 2680,
   :var-type "function",
   :arglists ([ns]),
   :doc "Returns a map of all the mappings for the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-map"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-name",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2675",
   :line 2675,
   :var-type "function",
   :arglists ([ns]),
   :doc "Returns the name of the namespace, a symbol.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-name"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-publics",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2694",
   :line 2694,
   :var-type "function",
   :arglists ([ns]),
   :doc
   "Returns a map of the public intern mappings for the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-publics"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-refers",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2737",
   :line 2737,
   :var-type "function",
   :arglists ([ns]),
   :doc "Returns a map of the refer mappings for the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-refers"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-resolve",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2819",
   :line 2819,
   :var-type "function",
   :arglists ([ns sym]),
   :doc
   "Returns the var or Class to which a symbol will be resolved in the\nnamespace, else nil.  Note that if the symbol is fully qualified,\nthe var/Class to which it resolves need not be present in the\nnamespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-resolve"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-unalias",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2766",
   :line 2766,
   :var-type "function",
   :arglists ([ns sym]),
   :doc "Removes the alias for the symbol from the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-unalias"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ns-unmap",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2685",
   :line 2685,
   :var-type "function",
   :arglists ([ns sym]),
   :doc "Removes the mappings for the symbol from the namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ns-unmap"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "nth",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L617",
   :line 617,
   :var-type "function",
   :arglists ([coll index] [coll index not-found]),
   :doc
   "Returns the value at the index. get returns nil if index out of\nbounds, nth throws an exception unless not-found is supplied.  nth\nalso works for strings, Java arrays, regex Matchers and Lists, and,\nin O(n) time, for sequences.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/nth"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "nthnext",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2836",
   :line 2836,
   :var-type "function",
   :arglists ([coll n]),
   :doc "Returns the nth next of coll, (seq coll) when n is 0.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/nthnext"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "num",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2204",
   :line 2204,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to Number",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/num"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "number?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2252",
   :line 2252,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is a Number",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/number?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "odd?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L923",
   :line 923,
   :var-type "function",
   :arglists ([n]),
   :doc
   "Returns true if n is odd, throws an exception if n is not an integer",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/odd?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "or",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L588",
   :line 588,
   :var-type "macro",
   :arglists ([] [x] [x & next]),
   :doc
   "Evaluates exprs one at a time, from left to right. If a form\nreturns a logical true value, or returns that value and doesn't\nevaluate any of the other expressions, otherwise it returns the\nvalue of the last expression. (or) returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/or"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "parents",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3717",
   :line 3717,
   :var-type "function",
   :arglists ([tag] [h tag]),
   :doc
   "Returns the immediate parents of tag, either via a Java type\ninheritance relationship or a relationship established via derive. h\nmust be a hierarchy obtained from make-hierarchy, if not supplied\ndefaults to the global hierarchy",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/parents"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "partial",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1670",
   :line 1670,
   :var-type "function",
   :arglists
   ([f arg1]
    [f arg1 arg2]
    [f arg1 arg2 arg3]
    [f arg1 arg2 arg3 & more]),
   :doc
   "Takes a function f and fewer than the normal arguments to f, and\nreturns a fn that takes a variable number of additional args. When\ncalled, the returned function calls f with args + additional args.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/partial"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "partition",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1991",
   :line 1991,
   :var-type "function",
   :arglists ([n coll] [n step coll] [n step pad coll]),
   :doc
   "Returns a lazy sequence of lists of n items each, at offsets step\napart. If step is not supplied, defaults to n, i.e. the partitions\ndo not overlap. If a pad collection is supplied, use its elements as\nnecessary to complete last partition upto n items. In case there are\nnot enough padding elements, return a partition with less than n items.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/partition"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pcalls",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4520",
   :line 4520,
   :var-type "function",
   :arglists ([& fns]),
   :doc
   "Executes the no-arg fns in parallel, returning a lazy sequence of\ntheir values",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pcalls"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "peek",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L955",
   :line 955,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "For a list or queue, same as first, for a vector, same as, but much\nmore efficient than, last. If the collection is empty, returns nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/peek"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "persistent!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4609",
   :line 4609,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Alpha - subject to change.\nReturns a new, persistent version of the transient collection, in\nconstant time. The transient collection cannot be used after this\ncall, any such use will throw an exception.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/persistent!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pmap",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4497",
   :line 4497,
   :var-type "function",
   :arglists ([f coll] [f coll & colls]),
   :doc
   "Like map, except f is applied in parallel. Semi-lazy in that the\nparallel computation stays ahead of the consumption, but doesn't\nrealize the entire result unless required. Only useful for\ncomputationally intensive functions where the time of f dominates\nthe coordination overhead.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pmap"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pop",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L960",
   :line 960,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "For a list or queue, returns a new list/queue without the first\nitem, for a vector, returns a new vector without the last item. If\nthe collection is empty, throws an exception.  Note - not the same\nas next/butlast.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pop"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pop!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4646",
   :line 4646,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Alpha - subject to change.\nRemoves the last item from a transient vector. If\nthe collection is empty, throws an exception. Returns coll",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pop!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pop-thread-bindings",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1239",
   :line 1239,
   :var-type "function",
   :arglists ([]),
   :doc
   "Pop one set of bindings pushed with push-binding before. It is an error to\npop bindings without pushing before.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pop-thread-bindings"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pos?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L839",
   :line 839,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if num is greater than zero, else false",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pos?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pr",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2325",
   :line 2325,
   :var-type "function",
   :arglists ([] [x] [x & more]),
   :doc
   "Prints the object(s) to the output stream that is the current value\nof *out*.  Prints the object(s), separated by spaces if there is\nmore than one.  By default, pr and prn print in a way that objects\ncan be read by the reader",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pr"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pr-str",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3121",
   :line 3121,
   :var-type "function",
   :arglists ([& xs]),
   :doc "pr to a string, returning it",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pr-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "prefer-method",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1162",
   :line 1162,
   :var-type "function",
   :arglists ([multifn dispatch-val-x dispatch-val-y]),
   :doc
   "Causes the multimethod to prefer matches of dispatch-val-x over dispatch-val-y \nwhen there is a conflict",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/prefer-method"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "prefers",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1177",
   :line 1177,
   :var-type "function",
   :arglists ([multifn]),
   :doc
   "Given a multimethod, returns a map of preferred value -> set of other values",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/prefers"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "print",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2360",
   :line 2360,
   :var-type "function",
   :arglists ([& more]),
   :doc
   "Prints the object(s) to the output stream that is the current value\nof *out*.  print and println produce output for human consumption.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/print"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "print-namespace-doc",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3281",
   :line 3281,
   :var-type "function",
   :arglists ([nspace]),
   :doc "Print the documentation string of a Namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/print-namespace-doc"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "print-str",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3135",
   :line 3135,
   :var-type "function",
   :arglists ([& xs]),
   :doc "print to a string, returning it",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/print-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "printf",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3863",
   :line 3863,
   :var-type "function",
   :arglists ([fmt & args]),
   :doc "Prints formatted output, as per format",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/printf"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "println",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2367",
   :line 2367,
   :var-type "function",
   :arglists ([& more]),
   :doc "Same as print followed by (newline)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/println"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "println-str",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3142",
   :line 3142,
   :var-type "function",
   :arglists ([& xs]),
   :doc "println to a string, returning it",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/println-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "prn",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2352",
   :line 2352,
   :var-type "function",
   :arglists ([& more]),
   :doc
   "Same as pr followed by (newline). Observes *flush-on-newline*",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/prn"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "prn-str",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3128",
   :line 3128,
   :var-type "function",
   :arglists ([& xs]),
   :doc "prn to a string, returning it",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/prn-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "promise",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4577",
   :line 4577,
   :var-type "function",
   :arglists ([]),
   :doc
   "Alpha - subject to change.\nReturns a promise object that can be read with deref/@, and set,\nonce only, with deliver. Calls to deref/@ prior to delivery will\nblock. All subsequent derefs will return the same delivered value\nwithout blocking.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/promise"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "proxy",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L295",
   :line 295,
   :var-type "macro",
   :arglists ([class-and-interfaces args & fs]),
   :doc
   "class-and-interfaces - a vector of class names\n\nargs - a (possibly empty) vector of arguments to the superclass\nconstructor.\n\nf => (name [params*] body) or\n(name ([params*] body) ([params+] body) ...)\n\nExpands to code which creates a instance of a proxy class that\nimplements the named class/interface(s) by calling the supplied\nfns. A single class, if provided, must be first. If not provided it\ndefaults to Object.\n\nThe interfaces names must be valid interface types. If a method fn\nis not provided for a class method, the superclass methd will be\ncalled. If a method fn is not provided for an interface method, an\nUnsupportedOperationException will be thrown should it be\ncalled. Method fns are closures and can capture the environment in\nwhich proxy is called. Each method fn takes an additional implicit\nfirst arg, which is bound to 'this. Note that while method fns can\nbe provided to override protected methods, they have no other access\nto protected members, nor to super, as these capabilities cannot be\nproxied.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/proxy"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "proxy-mappings",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L290",
   :line 290,
   :var-type "function",
   :arglists ([proxy]),
   :doc "Takes a proxy instance and returns the proxy's fn map.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/proxy-mappings"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "proxy-super",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L354",
   :line 354,
   :var-type "macro",
   :arglists ([meth & args]),
   :doc
   "Use to call a superclass method in the body of a proxy method. \nNote, expansion captures 'this",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/proxy-super"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "push-thread-bindings",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1223",
   :line 1223,
   :var-type "function",
   :arglists ([bindings]),
   :doc
   "WARNING: This is a low-level function. Prefer high-level macros like\nbinding where ever possible.\n\nTakes a map of Var/value pairs. Binds each Var to the associated value for\nthe current thread. Each call *MUST* be accompanied by a matching call to\npop-thread-bindings wrapped in a try-finally!\n\n    (push-thread-bindings bindings)\n    (try\n      ...\n      (finally\n        (pop-thread-bindings)))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/push-thread-bindings"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "pvalues",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4525",
   :line 4525,
   :var-type "macro",
   :arglists ([& exprs]),
   :doc
   "Returns a lazy sequence of the values of the exprs, which are\nevaluated in parallel",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/pvalues"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "quot",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L851",
   :line 851,
   :var-type "function",
   :arglists ([num div]),
   :doc "quot[ient] of dividing numerator by denominator.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/quot"}
  {:raw-source-url nil,
   :added "1.0",
   :name "quote",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Yields the unevaluated form.\n\nPlease see http://clojure.org/special_forms#quote",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/quote",
   :forms ['form]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "rand",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3226",
   :line 3226,
   :var-type "function",
   :arglists ([] [n]),
   :doc
   "Returns a random floating point number between 0 (inclusive) and\nn (default 1) (exclusive).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/rand"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "rand-int",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3232",
   :line 3232,
   :var-type "function",
   :arglists ([n]),
   :doc
   "Returns a random integer between 0 (inclusive) and n (exclusive).",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/rand-int"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "range",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1897",
   :line 1897,
   :var-type "function",
   :arglists ([end] [start end] [start end step]),
   :doc
   "Returns a lazy seq of nums from start (inclusive) to end\n(exclusive), by step, where start defaults to 0 and step to 1.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/range"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ratio?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2274",
   :line 2274,
   :var-type "function",
   :arglists ([n]),
   :doc "Returns true if n is a Ratio",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ratio?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "rationalize",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L861",
   :line 861,
   :var-type "function",
   :arglists ([num]),
   :doc "returns the rational value of num",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/rationalize"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "re-find",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3215",
   :line 3215,
   :var-type "function",
   :arglists ([m] [re s]),
   :doc
   "Returns the next regex match, if any, of string to pattern, using\njava.util.regex.Matcher.find().  Uses re-groups to return the\ngroups.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/re-find"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "re-groups",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3181",
   :line 3181,
   :var-type "function",
   :arglists ([m]),
   :doc
   "Returns the groups from the most recent match/find. If there are no\nnested groups, returns a string of the entire match. If there are\nnested groups, returns a vector of the groups, the first element\nbeing the entire match.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/re-groups"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "re-matcher",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3174",
   :line 3174,
   :var-type "function",
   :arglists ([re s]),
   :doc
   "Returns an instance of java.util.regex.Matcher, for use, e.g. in\nre-find.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/re-matcher"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "re-matches",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3205",
   :line 3205,
   :var-type "function",
   :arglists ([re s]),
   :doc
   "Returns the match, if any, of string to pattern, using\njava.util.regex.Matcher.matches().  Uses re-groups to return the\ngroups.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/re-matches"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "re-pattern",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3166",
   :line 3166,
   :var-type "function",
   :arglists ([s]),
   :doc
   "Returns an instance of java.util.regex.Pattern, for use, e.g. in\nre-matcher.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/re-pattern"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "re-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3195",
   :line 3195,
   :var-type "function",
   :arglists ([re s]),
   :doc
   "Returns a lazy sequence of successive matches of pattern in string,\nusing java.util.regex.Matcher.find(), each such match processed with\nre-groups.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/re-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "read",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2374",
   :line 2374,
   :var-type "function",
   :arglists
   ([]
    [stream]
    [stream eof-error? eof-value]
    [stream eof-error? eof-value recursive?]),
   :doc
   "Reads the next object from stream, which must be an instance of\njava.io.PushbackReader or some derivee.  stream defaults to the\ncurrent value of *in* .",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/read"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "read-line",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2387",
   :line 2387,
   :var-type "function",
   :arglists ([]),
   :doc
   "Reads the next line from stream that is the current value of *in* .",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/read-line"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "read-string",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2394",
   :line 2394,
   :var-type "function",
   :arglists ([s]),
   :doc "Reads one object from the string s",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/read-string"}
  {:raw-source-url nil,
   :added "1.0",
   :name "recur",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Evaluates the exprs in order, then, in parallel, rebinds\nthe bindings of the recursion point to the values of the exprs.\nExecution then jumps back to the recursion point, a loop or fn method.\n\nPlease see http://clojure.org/special_forms#recur",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/recur",
   :forms [(recur exprs*)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "reduce",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L646",
   :line 646,
   :var-type "function",
   :arglists ([f coll] [f val coll]),
   :doc
   "f should be a function of 2 arguments. If val is not supplied,\nreturns the result of applying f to the first 2 items in coll, then\napplying f to that result and the 3rd item, etc. If coll contains no\nitems, f must accept no arguments as well, and reduce returns the\nresult of calling f with no arguments.  If coll has only 1 item, it\nis returned and f is not called.  If val is supplied, returns the\nresult of applying f to val and the first item in coll, then\napplying f to that result and the 2nd item, etc. If coll contains no\nitems, returns val and f is not called.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/reduce"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ref",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1411",
   :line 1411,
   :var-type "function",
   :arglists ([x] [x & options]),
   :doc
   "Creates and returns a Ref with an initial value of x and zero or\nmore options (in any order):\n\n:meta metadata-map\n\n:validator validate-fn\n\n:min-history (default 0)\n:max-history (default 10)\n\nIf metadata-map is supplied, it will be come the metadata on the\nref. validate-fn must be nil or a side-effect-free fn of one\nargument, which will be passed the intended new state on any state\nchange. If the new state is unacceptable, the validate-fn should\nreturn false or throw an exception. validate-fn will be called on\ntransaction commit, when all refs have their final values.\n\nNormally refs accumulate history dynamically as needed to deal with\nread demands. If you know in advance you will need history you can\nset :min-history to ensure it will be available when first needed (instead\nof after a read fault). History is limited, and the limit can be set\nwith :max-history.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ref"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ref-history-count",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1550",
   :line 1550,
   :var-type "function",
   :arglists ([ref]),
   :doc "Returns the history count of a ref",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ref-history-count"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ref-max-history",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1562",
   :line 1562,
   :var-type "function",
   :arglists ([ref] [ref n]),
   :doc
   "Gets the max-history of a ref, or sets it and returns the ref",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ref-max-history"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ref-min-history",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1555",
   :line 1555,
   :var-type "function",
   :arglists ([ref] [ref n]),
   :doc
   "Gets the min-history of a ref, or sets it and returns the ref",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ref-min-history"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "ref-set",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1544",
   :line 1544,
   :var-type "function",
   :arglists ([ref val]),
   :doc
   "Must be called in a transaction. Sets the value of ref.\nReturns val.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/ref-set"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "refer",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2708",
   :line 2708,
   :var-type "function",
   :arglists ([ns-sym & filters]),
   :doc
   "refers to all public vars of ns, subject to filters.\nfilters can include at most one each of:\n\n:exclude list-of-symbols\n:only list-of-symbols\n:rename map-of-fromsymbol-tosymbol\n\nFor each public interned var in the namespace named by the symbol,\nadds a mapping from the name of the var to the var to the current\nnamespace.  Throws an exception if name is already mapped to\nsomething else in the current namespace. Filters can be used to\nselect a subset, via inclusion or exclusion, or to provide a mapping\nto a symbol different from the var's name, in order to prevent\nclashes. Use :use in the ns macro in preference to calling this directly.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/refer"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "refer-clojure",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3927",
   :line 3927,
   :var-type "macro",
   :arglists ([& filters]),
   :doc "Same as (refer 'clojure.core <filters>)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/refer-clojure"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "release-pending-sends",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1362",
   :line 1362,
   :var-type "function",
   :arglists ([]),
   :doc
   "Normally, actions sent directly or indirectly during another action\nare held until the action completes (changes the agent's\nstate). This function can be used to dispatch any pending sent\nactions immediately. This has no impact on actions sent during a\ntransaction, which are still held until commit. If no action is\noccurring, does nothing. Returns the number of actions dispatched.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/release-pending-sends"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "rem",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L856",
   :line 856,
   :var-type "function",
   :arglists ([num div]),
   :doc "remainder of dividing numerator by denominator.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/rem"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "remove",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1809",
   :line 1809,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns a lazy sequence of the items in coll for which\n(pred item) returns false. pred must be free of side-effects.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/remove"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "remove-method",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1157",
   :line 1157,
   :var-type "function",
   :arglists ([multifn dispatch-val]),
   :doc
   "Removes the method of multimethod associated with dispatch-value.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/remove-method"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "remove-ns",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2657",
   :line 2657,
   :var-type "function",
   :arglists ([sym]),
   :doc
   "Removes the namespace named by the symbol. Use with caution.\nCannot be used to remove the clojure namespace.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/remove-ns"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "remove-watch",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1388",
   :line 1388,
   :var-type "function",
   :arglists ([reference key]),
   :doc
   "Alpha - subject to change.\nRemoves a watch (set by add-watch) from a reference",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/remove-watch"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "repeat",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1884",
   :line 1884,
   :var-type "function",
   :arglists ([x] [n x]),
   :doc
   "Returns a lazy (infinite!, or length n if supplied) sequence of xs.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/repeat"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "repeatedly",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3467",
   :line 3467,
   :var-type "function",
   :arglists ([f]),
   :doc
   "Takes a function of no args, presumably with side effects, and returns an infinite\nlazy sequence of calls to it",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/repeatedly"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "replace",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3393",
   :line 3393,
   :var-type "function",
   :arglists ([smap coll]),
   :doc
   "Given a map of replacement pairs and a vector/collection, returns a\nvector/seq with any elements = a key in smap replaced with the\ncorresponding val in smap",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/replace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "replicate",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1889",
   :line 1889,
   :var-type "function",
   :arglists ([n x]),
   :doc "Returns a lazy seq of n xs.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/replicate"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "require",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4079",
   :line 4079,
   :var-type "function",
   :arglists ([& args]),
   :doc
   "Loads libs, skipping any that are already loaded. Each argument is\neither a libspec that identifies a lib, a prefix list that identifies\nmultiple libs whose names share a common prefix, or a flag that modifies\nhow all the identified libs are loaded. Use :require in the ns macro\nin preference to calling this directly.\n\nLibs\n\nA 'lib' is a named set of resources in classpath whose contents define a\nlibrary of Clojure code. Lib names are symbols and each lib is associated\nwith a Clojure namespace and a Java package that share its name. A lib's\nname also locates its root directory within classpath using Java's\npackage name to classpath-relative path mapping. All resources in a lib\nshould be contained in the directory structure under its root directory.\nAll definitions a lib makes should be in its associated namespace.\n\n'require loads a lib by loading its root resource. The root resource path\nis derived from the lib name in the following manner:\nConsider a lib named by the symbol 'x.y.z; it has the root directory\n<classpath>/x/y/, and its root resource is <classpath>/x/y/z.clj. The root\nresource should contain code to create the lib's namespace (usually by using\nthe ns macro) and load any additional lib resources.\n\nLibspecs\n\nA libspec is a lib name or a vector containing a lib name followed by\noptions expressed as sequential keywords and arguments.\n\nRecognized options: :as\n:as takes a symbol as its argument and makes that symbol an alias to the\n  lib's namespace in the current namespace.\n\nPrefix Lists\n\nIt's common for Clojure code to depend on several libs whose names have\nthe same prefix. When specifying libs, prefix lists can be used to reduce\nrepetition. A prefix list contains the shared prefix followed by libspecs\nwith the shared prefix removed from the lib names. After removing the\nprefix, the names that remain must not contain any periods.\n\nFlags\n\nA flag is a keyword.\nRecognized flags: :reload, :reload-all, :verbose\n:reload forces loading of all the identified libs even if they are\n  already loaded\n:reload-all implies :reload and also forces loading of all libs that the\n  identified libs directly or indirectly load via require or use\n:verbose triggers printing information about each load, alias, and refer\n\nExample:\n\nThe following would load the libraries clojure.zip and clojure.set\nabbreviated as 's'.\n\n(require '(clojure zip [set :as s]))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/require"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "reset!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1485",
   :line 1485,
   :var-type "function",
   :arglists ([atom newval]),
   :doc
   "Sets the value of atom to newval without regard for the\ncurrent value. Returns newval.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/reset!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "reset-meta!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1511",
   :line 1511,
   :var-type "function",
   :arglists ([iref metadata-map]),
   :doc
   "Atomically resets the metadata for a namespace/var/ref/agent/atom",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/reset-meta!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "resolve",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2827",
   :line 2827,
   :var-type "function",
   :arglists ([sym]),
   :doc "same as (ns-resolve *ns* symbol)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/resolve"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "rest",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L54",
   :line 54,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns a possibly empty seq of the items after the first. Calls seq on its\nargument.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/rest"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "resultset-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3827",
   :line 3827,
   :var-type "function",
   :arglists ([rs]),
   :doc
   "Creates and returns a lazy sequence of structmaps corresponding to\nthe rows in the java.sql.ResultSet rs",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/resultset-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "reverse",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L671",
   :line 671,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns a seq of the items in coll in reverse order. Not lazy.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/reverse"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "reversible?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4255",
   :line 4255,
   :var-type "function",
   :arglists ([coll]),
   :doc "Returns true if coll implements Reversible",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/reversible?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "rseq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1043",
   :line 1043,
   :var-type "function",
   :arglists ([rev]),
   :doc
   "Returns, in constant time, a seq of the items in rev (which\ncan be a vector or sorted-map), in reverse order. If rev is empty returns nil",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/rseq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "rsubseq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3452",
   :line 3452,
   :var-type "function",
   :arglists
   ([sc test key] [sc start-test start-key end-test end-key]),
   :doc
   "sc must be a sorted collection, test(s) one of <, <=, > or\n>=. Returns a reverse seq of those entries with keys ek for\nwhich (test (.. sc comparator (compare ek key)) 0) is true",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/rsubseq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "second",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L73",
   :line 73,
   :var-type "function",
   :arglists ([x]),
   :doc "Same as (first (next x))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/second"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "select-keys",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1012",
   :line 1012,
   :var-type "function",
   :arglists ([map keyseq]),
   :doc
   "Returns a map containing only those entries in map whose key is in keys",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/select-keys"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "send",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1344",
   :line 1344,
   :var-type "function",
   :arglists ([a f & args]),
   :doc
   "Dispatch an action to an agent. Returns the agent immediately.\nSubsequently, in a thread from a thread pool, the state of the agent\nwill be set to the value of:\n\n(apply action-fn state-of-agent args)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/send"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "send-off",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1353",
   :line 1353,
   :var-type "function",
   :arglists ([a f & args]),
   :doc
   "Dispatch a potentially blocking action to an agent. Returns the\nagent immediately. Subsequently, in a separate thread, the state of\nthe agent will be set to the value of:\n\n(apply action-fn state-of-agent args)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/send-off"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L98",
   :line 98,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns a seq on the collection. If the collection is\nempty, returns nil.  (seq nil) returns nil. seq also works on\nStrings, native Java arrays (of reference types) and any objects\nthat implement Iterable.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "seq?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L113",
   :line 113,
   :var-type "function",
   :arglists ([x]),
   :doc "Return true if x implements ISeq",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/seq?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "seque",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3624",
   :line 3624,
   :var-type "function",
   :arglists ([s] [n-or-q s]),
   :doc
   "Creates a queued seq on another (presumably lazy) seq s. The queued\nseq will produce a concrete seq in the background, and can get up to\nn items ahead of the consumer. n-or-q can be an integer n buffer\nsize, or an instance of java.util.concurrent BlockingQueue. Note\nthat reading from a seque can block if the reader gets ahead of the\nproducer.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/seque"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sequence",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1689",
   :line 1689,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Coerces coll to a (possibly empty) sequence, if it is not already\none. Will not force a lazy seq. (sequence nil) yields ()",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sequence"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sequential?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4243",
   :line 4243,
   :var-type "function",
   :arglists ([coll]),
   :doc "Returns true if coll implements Sequential",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sequential?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "set",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2634",
   :line 2634,
   :var-type "function",
   :arglists ([coll]),
   :doc "Returns a set of the distinct elements of coll.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/set"}
  {:raw-source-url nil,
   :added "1.0",
   :name "set!",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "Used to set thread-local-bound vars, Java object instance\nfields, and Java class static fields.\n\nPlease see http://clojure.org/vars#set",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/set!",
   :forms
   [(set! var-symbol expr)
    (set! (. instance-expr instanceFieldName-symbol) expr)
    (set! (. Classname-symbol staticFieldName-symbol) expr)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "set-validator!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1490",
   :line 1490,
   :var-type "function",
   :arglists ([iref validator-fn]),
   :doc
   "Sets the validator-fn for a var/ref/agent/atom. validator-fn must be nil or a\nside-effect-free fn of one argument, which will be passed the intended\nnew state on any state change. If the new state is unacceptable, the\nvalidator-fn should return false or throw an exception. If the current state (root\nvalue if var) is not acceptable to the new validator, an exception\nwill be thrown and the validator will not be changed.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/set-validator!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "set?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4225",
   :line 4225,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x implements IPersistentSet",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/set?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "short",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2228",
   :line 2228,
   :var-type "function",
   :arglists ([x]),
   :doc "Coerce to short",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/short"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "short-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3562",
   :line 3562,
   :var-type "function",
   :arglists ([size-or-seq] [size init-val-or-seq]),
   :doc "Creates an array of shorts",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/short-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "shorts",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3602",
   :line 3602,
   :var-type "function",
   :arglists ([xs]),
   :doc "Casts to shorts[]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/shorts"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "shutdown-agents",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1405",
   :line 1405,
   :var-type "function",
   :arglists ([]),
   :doc
   "Initiates a shutdown of the thread pools that back the agent\nsystem. Running actions will complete, but no new actions will be\naccepted",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/shutdown-agents"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "slurp",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3342",
   :line 3342,
   :var-type "function",
   :arglists ([f] [f enc]),
   :doc
   "Reads the file named by f using the encoding enc into a string\nand returns it.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/slurp"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "some",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1715",
   :line 1715,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns the first logical true value of (pred x) for any x in coll,\nelse nil.  One common idiom is to use a set as pred, for example\nthis will return :fred if :fred is in the sequence, otherwise nil:\n(some #{:fred} coll)",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/some"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sort",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1968",
   :line 1968,
   :var-type "function",
   :arglists ([coll] [comp coll]),
   :doc
   "Returns a sorted sequence of the items in coll. If no comparator is\nsupplied, uses compare. comparator must\nimplement java.util.Comparator.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sort"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sort-by",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1981",
   :line 1981,
   :var-type "function",
   :arglists ([keyfn coll] [keyfn comp coll]),
   :doc
   "Returns a sorted sequence of the items in coll, where the sort\norder is determined by comparing (keyfn item).  If no comparator is\nsupplied, uses compare. comparator must\nimplement java.util.Comparator.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sort-by"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sorted-map",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L287",
   :line 287,
   :var-type "function",
   :arglists ([& keyvals]),
   :doc
   "keyval => key val\nReturns a new sorted map with supplied mappings.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sorted-map"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sorted-map-by",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L293",
   :line 293,
   :var-type "function",
   :arglists ([comparator & keyvals]),
   :doc
   "keyval => key val\nReturns a new sorted map with supplied mappings, using the supplied comparator.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sorted-map-by"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sorted-set",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L299",
   :line 299,
   :var-type "function",
   :arglists ([& keys]),
   :doc "Returns a new sorted set with supplied keys.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sorted-set"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sorted-set-by",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L304",
   :line 304,
   :var-type "function",
   :arglists ([comparator & keys]),
   :doc
   "Returns a new sorted set with supplied keys, using the supplied comparator.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sorted-set-by"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sorted?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4247",
   :line 4247,
   :var-type "function",
   :arglists ([coll]),
   :doc "Returns true if coll implements Sorted",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sorted?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "special-form-anchor",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3261",
   :line 3261,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns the anchor tag on http://clojure.org/special_forms for the\nspecial form x, or nil",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/special-form-anchor"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "special-symbol?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3333",
   :line 3333,
   :var-type "function",
   :arglists ([s]),
   :doc "Returns true if s names a special form",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/special-symbol?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "split-at",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1874",
   :line 1874,
   :var-type "function",
   :arglists ([n coll]),
   :doc "Returns a vector of [(take n coll) (drop n coll)]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/split-at"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "split-with",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1879",
   :line 1879,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns a vector of [(take-while pred coll) (drop-while pred coll)]",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/split-with"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "str",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L356",
   :line 356,
   :var-type "function",
   :arglists ([] [x] [x & ys]),
   :doc
   "With no args, returns the empty string. With one arg x, returns\nx.toString().  (str nil) returns the empty string. With more than\none arg, returns the concatenation of the str values of the args.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "stream?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1684",
   :line 1684,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is an instance of Stream",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/stream?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "string?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L123",
   :line 123,
   :var-type "function",
   :arglists ([x]),
   :doc "Return true if x is a String",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/string?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "struct",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2605",
   :line 2605,
   :var-type "function",
   :arglists ([s & vals]),
   :doc
   "Returns a new structmap instance with the keys of the\nstructure-basis. vals must be supplied for basis keys in order -\nwhere values are not supplied they will default to nil.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/struct"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "struct-map",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2597",
   :line 2597,
   :var-type "function",
   :arglists ([s & inits]),
   :doc
   "Returns a new structmap instance with the keys of the\nstructure-basis. keyvals may contain all, some or none of the basis\nkeys - where values are not supplied they will default to nil.\nkeyvals can also contain keys not in the basis.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/struct-map"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "subs",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3358",
   :line 3358,
   :var-type "function",
   :arglists ([s start] [s start end]),
   :doc
   "Returns the substring of s beginning at start inclusive, and ending\nat end (defaults to length of string), exclusive.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/subs"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "subseq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3437",
   :line 3437,
   :var-type "function",
   :arglists
   ([sc test key] [sc start-test start-key end-test end-key]),
   :doc
   "sc must be a sorted collection, test(s) one of <, <=, > or\n>=. Returns a seq of those entries with keys ek for\nwhich (test (.. sc comparator (compare ek key)) 0) is true",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/subseq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "subvec",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2398",
   :line 2398,
   :var-type "function",
   :arglists ([v start] [v start end]),
   :doc
   "Returns a persistent vector of the items in vector from\nstart (inclusive) to end (exclusive).  If end is not supplied,\ndefaults to (count vector). This operation is O(1) and very fast, as\nthe resulting vector shares structure with the original and no\ntrimming is done.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/subvec"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "supers",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3688",
   :line 3688,
   :var-type "function",
   :arglists ([class]),
   :doc
   "Returns the immediate and indirect superclasses and interfaces of c, if any",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/supers"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "swap!",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1469",
   :line 1469,
   :var-type "function",
   :arglists ([atom f] [atom f x] [atom f x y] [atom f x y & args]),
   :doc
   "Atomically swaps the value of atom to be:\n(apply f current-value-of-atom args). Note that f may be called\nmultiple times, and thus should be free of side effects.  Returns\nthe value that was swapped in.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/swap!"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "symbol",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L380",
   :line 380,
   :var-type "function",
   :arglists ([name] [ns name]),
   :doc "Returns a Symbol with the given namespace and name.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/symbol"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "symbol?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L372",
   :line 372,
   :var-type "function",
   :arglists ([x]),
   :doc "Return true if x is a Symbol",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/symbol?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "sync",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1577",
   :line 1577,
   :var-type "macro",
   :arglists ([flags-ignored-for-now & body]),
   :doc
   "transaction-flags => TBD, pass nil for now\n\nRuns the exprs (in an implicit do) in a transaction that encompasses\nexprs and any nested calls.  Starts a transaction if none is already\nrunning on this thread. Any uncaught exception will abort the\ntransaction and flow out of sync. The exprs may be run more than\nonce, but any effects on Refs will be atomic.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/sync"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "syntax-symbol-anchor",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3268",
   :line 3268,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns the anchor tag on http://clojure.org/special_forms for the\nspecial form that uses syntax symbol x, or nil",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/syntax-symbol-anchor"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "take",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1815",
   :line 1815,
   :var-type "function",
   :arglists ([n coll]),
   :doc
   "Returns a lazy sequence of the first n items in coll, or all items if\nthere are fewer than n.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/take"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "take-last",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1848",
   :line 1848,
   :var-type "function",
   :arglists ([n coll]),
   :doc
   "Returns a seq of the last n items in coll.  Depending on the type\nof coll may be no better than linear time.  For vectors, see also subvec.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/take-last"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "take-nth",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2771",
   :line 2771,
   :var-type "function",
   :arglists ([n coll]),
   :doc "Returns a lazy seq of every nth item in coll.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/take-nth"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "take-while",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1824",
   :line 1824,
   :var-type "function",
   :arglists ([pred coll]),
   :doc
   "Returns a lazy sequence of successive items from coll while\n(pred item) returns true. pred must be free of side-effects.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/take-while"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "test",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3157",
   :line 3157,
   :var-type "function",
   :arglists ([v]),
   :doc
   "test [v] finds fn at key :test in var metadata and calls it,\npresuming failure will throw exception",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/test"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "the-ns",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2666",
   :line 2666,
   :var-type "function",
   :arglists ([x]),
   :doc
   "If passed a namespace, returns it. Else, when passed a symbol,\nreturns the namespace named by it, throwing an exception if not\nfound.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/the-ns"}
  {:raw-source-url nil,
   :added "1.0",
   :name "throw",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "The expr is evaluated and thrown, therefore it should\nyield an instance of some derivee of Throwable.\n\nPlease see http://clojure.org/special_forms#throw",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/throw",
   :forms [(throw expr)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "time",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2454",
   :line 2454,
   :var-type "macro",
   :arglists ([expr]),
   :doc
   "Evaluates expr and prints the time it took.  Returns the value of\nexpr.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/time"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "to-array",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L255",
   :line 255,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns an array of Objects containing the contents of coll, which\ncan be any Collection.  Maps to java.util.Collection.toArray().",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/to-array"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "to-array-2d",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2558",
   :line 2558,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Returns a (potentially-ragged) 2-dimensional array of Objects\ncontaining the contents of coll, which can be any Collection of any\nCollection.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/to-array-2d"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "trampoline",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4279",
   :line 4279,
   :var-type "function",
   :arglists ([f] [f & args]),
   :doc
   "trampoline can be used to convert algorithms requiring mutual\nrecursion without stack consumption. Calls f with supplied args, if\nany. If f returns a fn, calls that fn with no arguments, and\ncontinues to repeat, until the return value is not a fn, then\nreturns that non-fn value. Note that if you want to return a fn as a\nfinal value, you must wrap it in some data structure and unpack it\nafter trampoline returns.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/trampoline"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "transient",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4603",
   :line 4603,
   :var-type "function",
   :arglists ([coll]),
   :doc
   "Alpha - subject to change.\nReturns a new, transient version of the collection, in constant time.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/transient"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "tree-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3302",
   :line 3302,
   :var-type "function",
   :arglists ([branch? children root]),
   :doc
   "Returns a lazy sequence of the nodes in a tree, via a depth-first walk.\n branch? must be a fn of one arg that returns true if passed a node\n that can have children (but may not).  children must be a fn of one\n arg that returns a sequence of the children. Will only be called on\n nodes for which branch? returns true. Root is the root node of the\ntree.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/tree-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "true?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L346",
   :line 346,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if x is the value true, false otherwise.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/true?"}
  {:raw-source-url nil,
   :added "1.0",
   :name "try",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "catch-clause => (catch classname name expr*)\nfinally-clause => (finally expr*)\n\nCatches and handles Java exceptions.\n\nPlease see http://clojure.org/special_forms#try",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/try",
   :forms [(try expr* catch-clause* finally-clause?)]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "type",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2199",
   :line 2199,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns the :type metadata of x, or its Class if none",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/type"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-add",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L809",
   :line 809,
   :var-type "function",
   :arglists ([x y]),
   :doc
   "Returns the sum of x and y, both int or long.\nNote - uses a primitive operator subject to overflow.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-add"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-dec",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L797",
   :line 797,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns a number one less than x, an int or long.\nNote - uses a primitive operator subject to overflow.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-dec"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-divide",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L827",
   :line 827,
   :var-type "function",
   :arglists ([x y]),
   :doc
   "Returns the division of x by y, both int or long.\nNote - uses a primitive operator subject to truncation.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-divide"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-inc",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L791",
   :line 791,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns a number one greater than x, an int or long.\nNote - uses a primitive operator subject to overflow.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-inc"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-multiply",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L821",
   :line 821,
   :var-type "function",
   :arglists ([x y]),
   :doc
   "Returns the product of x and y, both int or long.\nNote - uses a primitive operator subject to overflow.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-multiply"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-negate",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L803",
   :line 803,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns the negation of x, an int or long.\nNote - uses a primitive operator subject to overflow.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-negate"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-remainder",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L833",
   :line 833,
   :var-type "function",
   :arglists ([x y]),
   :doc
   "Returns the remainder of division of x by y, both int or long.\nNote - uses a primitive operator subject to truncation.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-remainder"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "unchecked-subtract",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L815",
   :line 815,
   :var-type "function",
   :arglists ([x y]),
   :doc
   "Returns the difference of x and y, both int or long.\nNote - uses a primitive operator subject to overflow.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/unchecked-subtract"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "underive",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3790",
   :line 3790,
   :var-type "function",
   :arglists ([tag parent] [h tag parent]),
   :doc
   "Removes a parent/child relationship between parent and\ntag. h must be a hierarchy obtained from make-hierarchy, if not\nsupplied defaults to, and modifies, the global hierarchy.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/underive"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "update-in",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4200",
   :line 4200,
   :var-type "function",
   :arglists ([m [k & ks] f & args]),
   :doc
   "'Updates' a value in a nested associative structure, where ks is a\nsequence of keys and f is a function that will take the old value\nand any supplied args and return the new value, and returns a new\nnested structure.  If any levels do not exist, hash-maps will be\ncreated.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/update-in"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj",
   :name "update-proxy",
   :file "src/clj/clojure/core_proxy.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/040f083efc16dd830a4508a35a04465e3e5677d3/src/clj/clojure/core_proxy.clj#L278",
   :line 278,
   :var-type "function",
   :arglists ([proxy mappings]),
   :doc
   "Takes a proxy instance and a map of strings (which must\ncorrespond to methods of the proxy superclass/superinterfaces) to\nfns (which must take arguments matching the corresponding method,\nplus an additional (explicit) first arg corresponding to this, and\nupdates (via assoc) the proxy's fn map. nil can be passed instead of\na fn, in which case the corresponding method will revert to the\ndefault behavior. Note that this function can be used to update the\nbehavior of an existing instance without changing its identity.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/update-proxy"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "use",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4140",
   :line 4140,
   :var-type "function",
   :arglists ([& args]),
   :doc
   "Like 'require, but also refers to each lib's namespace using\nclojure.core/refer. Use :use in the ns macro in preference to calling\nthis directly.\n\n'use accepts additional options in libspecs: :exclude, :only, :rename.\nThe arguments and semantics for :exclude, :only, and :rename are the same\nas those documented for clojure.core/refer.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/use"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "val",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1038",
   :line 1038,
   :var-type "function",
   :arglists ([e]),
   :doc "Returns the value in the map entry.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/val"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "vals",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1029",
   :line 1029,
   :var-type "function",
   :arglists ([map]),
   :doc "Returns a sequence of the map's values.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/vals"}
  {:raw-source-url nil,
   :added "1.0",
   :name "var",
   :file nil,
   :source-url nil,
   :var-type "special form",
   :arglists nil,
   :doc
   "The symbol must resolve to a var, and the Var object\nitself (not its value) is returned. The reader macro #'x\nexpands to (var x).\n\nPlease see http://clojure.org/special_forms#var",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/var",
   :forms [#'symbol]}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "var-get",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2792",
   :line 2792,
   :var-type "function",
   :arglists ([x]),
   :doc "Gets the value in the var object",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/var-get"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "var-set",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2796",
   :line 2796,
   :var-type "function",
   :arglists ([x val]),
   :doc
   "Sets the value in the var object to val. The var must be\nthread-locally bound.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/var-set"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "var?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3338",
   :line 3338,
   :var-type "function",
   :arglists ([v]),
   :doc "Returns true if v is of type clojure.lang.Var",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/var?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "vary-meta",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L446",
   :line 446,
   :var-type "function",
   :arglists ([obj f & args]),
   :doc
   "Returns an object of the same type and value as obj, with\n(apply f (meta obj) args) as its metadata.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/vary-meta"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "vec",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L267",
   :line 267,
   :var-type "function",
   :arglists ([coll]),
   :doc "Creates a new vector containing the contents of coll.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/vec"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "vector",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L261",
   :line 261,
   :var-type "function",
   :arglists ([] [& args]),
   :doc "Creates a new vector containing the args.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/vector"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "vector?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L133",
   :line 133,
   :var-type "function",
   :arglists ([x]),
   :doc "Return true if x implements IPersistentVector ",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/vector?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "when",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L326",
   :line 326,
   :var-type "macro",
   :arglists ([test & body]),
   :doc
   "Evaluates test. If logical true, evaluates body in an implicit do.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/when"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "when-first",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2991",
   :line 2991,
   :var-type "macro",
   :arglists ([bindings & body]),
   :doc
   "bindings => x xs\n\nSame as (when (seq xs) (let [x (first xs)] body))",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/when-first"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "when-let",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1209",
   :line 1209,
   :var-type "macro",
   :arglists ([bindings & body]),
   :doc
   "bindings => binding-form test\n\nWhen test is true, evaluates body with binding-form bound to the value of test",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/when-let"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "when-not",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L331",
   :line 331,
   :var-type "macro",
   :arglists ([test & body]),
   :doc
   "Evaluates test. If logical false, evaluates body in an implicit do.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/when-not"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "while",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L4309",
   :line 4309,
   :var-type "macro",
   :arglists ([test & body]),
   :doc
   "Repeatedly executes body while test expression is true. Presumes\nsome side-effect will cause test to become false/nil. Returns nil",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/while"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-bindings",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1287",
   :line 1287,
   :var-type "macro",
   :arglists ([binding-map & body]),
   :doc
   "Takes a map of Var/value pairs. Installs for the given Vars the associated\nvalues as thread-local bindings. The executes body. Pops the installed\nbindings after body was evaluated. Returns the value of body.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-bindings"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-bindings*",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1276",
   :line 1276,
   :var-type "function",
   :arglists ([binding-map f & args]),
   :doc
   "Takes a map of Var/value pairs. Installs for the given Vars the associated\nvalues as thread-local bindings. Then calls f with the supplied arguments.\nPops the installed bindings after f returned. Returns whatever f returns.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-bindings*"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-in-str",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3113",
   :line 3113,
   :var-type "macro",
   :arglists ([s & body]),
   :doc
   "Evaluates body in a context in which *in* is bound to a fresh\nStringReader initialized with the string s.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-in-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-local-vars",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2801",
   :line 2801,
   :var-type "macro",
   :arglists ([name-vals-vec & body]),
   :doc
   "varbinding=> symbol init-expr\n\nExecutes the exprs in a context in which the symbols are bound to\nvars with per-thread bindings to the init-exprs.  The symbols refer\nto the var objects themselves, and must be accessed with var-get and\nvar-set",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-local-vars"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-meta",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L161",
   :line 161,
   :var-type "function",
   :arglists ([obj m]),
   :doc
   "Returns an object of the same type and value as obj, with\nmap m as its metadata.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-meta"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-open",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L2409",
   :line 2409,
   :var-type "macro",
   :arglists ([bindings & body]),
   :doc
   "bindings => [name init ...]\n\nEvaluates body in a try expression with names bound to the values\nof the inits, and a finally clause that calls (.close name) on each\nname in reverse order.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-open"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-out-str",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3103",
   :line 3103,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "Evaluates exprs in a context in which *out* is bound to a fresh\nStringWriter.  Returns the string created by any nested printing\ncalls.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-out-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "with-precision",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3415",
   :line 3415,
   :var-type "macro",
   :arglists ([precision & exprs]),
   :doc
   "Sets the precision and rounding mode to be used for BigDecimal operations.\n\nUsage: (with-precision 10 (/ 1M 3))\nor:    (with-precision 10 :rounding HALF_DOWN (/ 1M 3))\n\nThe rounding mode is one of CEILING, FLOOR, HALF_UP, HALF_DOWN,\nHALF_EVEN, UP, DOWN and UNNECESSARY; it defaults to HALF_UP.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/with-precision"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "xml-seq",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L3325",
   :line 3325,
   :var-type "function",
   :arglists ([root]),
   :doc "A tree seq on the xml elements as per xml/parse",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/xml-seq"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "zero?",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L600",
   :line 600,
   :var-type "function",
   :arglists ([x]),
   :doc "Returns true if num is zero, else false",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/zero?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj",
   :name "zipmap",
   :file "src/clj/clojure/core.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/f4c58e3500b3668a0941ca21f9aa4f444de2c652/src/clj/clojure/core.clj#L1942",
   :line 1942,
   :var-type "function",
   :arglists ([keys vals]),
   :doc
   "Returns a map with the keys mapped to the corresponding vals.",
   :namespace "clojure.core",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.core-api.html#clojure.core/zipmap"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/inspector.clj",
   :name "inspect",
   :file "src/clj/clojure/inspector.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/inspector.clj#L148",
   :line 148,
   :var-type "function",
   :arglists ([x]),
   :doc "creates a graphical (Swing) inspector on the supplied object",
   :namespace "clojure.inspector",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.inspector-api.html#clojure.inspector/inspect"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/inspector.clj",
   :name "inspect-table",
   :file "src/clj/clojure/inspector.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/inspector.clj#L95",
   :line 95,
   :var-type "function",
   :arglists ([data]),
   :doc
   "creates a graphical (Swing) inspector on the supplied regular\ndata, which must be a sequential data structure of data structures\nof equal length",
   :namespace "clojure.inspector",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.inspector-api.html#clojure.inspector/inspect-table"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/inspector.clj",
   :name "inspect-tree",
   :file "src/clj/clojure/inspector.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/inspector.clj#L87",
   :line 87,
   :var-type "function",
   :arglists ([data]),
   :doc
   "creates a graphical (Swing) inspector on the supplied hierarchical data",
   :namespace "clojure.inspector",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.inspector-api.html#clojure.inspector/inspect-tree"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "load-script",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L206",
   :line 206,
   :var-type "function",
   :arglists ([path]),
   :doc
   "Loads Clojure source from a file or resource given its path. Paths\nbeginning with @ or @/ are considered relative to classpath.",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/load-script"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "main",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L310",
   :line 310,
   :var-type "function",
   :arglists ([& args]),
   :doc
   "Usage: java -cp clojure.jar clojure.main [init-opt*] [main-opt] [arg*]\n\nWith no options or args, runs an interactive Read-Eval-Print Loop\n\ninit options:\n  -i, --init path   Load a file or resource\n  -e, --eval string Evaluate expressions in string; print non-nil values\n\nmain options:\n  -r, --repl        Run a repl\n  path              Run a script from from a file or resource\n  -                 Run a script from standard input\n  -h, -?, --help    Print this help message and exit\n\noperation:\n\n  - Establishes thread-local bindings for commonly set!-able vars\n  - Enters the user namespace\n  - Binds *command-line-args* to a seq of strings containing command line\n    args that appear after any main option\n  - Runs all init options in order\n  - Runs a repl or script if requested\n\nThe init options may be repeated and mixed freely, but must appear before\nany main option. The appearance of any eval option before running a repl\nsuppresses the usual repl greeting message: \"Clojure ~(clojure-version)\".\n\nPaths may be absolute or relative in the filesystem or relative to\nclasspath. Classpath-relative paths have prefix of @ or @/",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/main"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "repl",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L118",
   :line 118,
   :var-type "function",
   :arglists ([& options]),
   :doc
   "Generic, reusable, read-eval-print loop. By default, reads from *in*,\nwrites to *out*, and prints exception summaries to *err*. If you use the\ndefault :read hook, *in* must either be an instance of\nLineNumberingPushbackReader or duplicate its behavior of both supporting\n.unread and collapsing CR, LF, and CRLF into a single \\newline. Options\nare sequential keyword-value pairs. Available options and their defaults:\n\n   - :init, function of no arguments, initialization hook called with\n     bindings for set!-able vars in place.\n     default: #()\n\n   - :need-prompt, function of no arguments, called before each\n     read-eval-print except the first, the user will be prompted if it\n     returns true.\n     default: (if (instance? LineNumberingPushbackReader *in*)\n                #(.atLineStart *in*)\n                #(identity true))\n\n   - :prompt, function of no arguments, prompts for more input.\n     default: repl-prompt\n\n   - :flush, function of no arguments, flushes output\n     default: flush\n\n   - :read, function of two arguments, reads from *in*:\n       - returns its first argument to request a fresh prompt\n         - depending on need-prompt, this may cause the repl to prompt\n           before reading again\n       - returns its second argument to request an exit from the repl\n       - else returns the next object read from the input stream\n     default: repl-read\n\n   - :eval, funtion of one argument, returns the evaluation of its\n     argument\n     default: eval\n\n   - :print, function of one argument, prints its argument to the output\n     default: prn\n\n   - :caught, function of one argument, a throwable, called when\n     read, eval, or print throws an exception or error\n     default: repl-caught",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/repl"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "repl-caught",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L113",
   :line 113,
   :var-type "function",
   :arglists ([e]),
   :doc "Default :caught hook for repl",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/repl-caught"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "repl-exception",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L105",
   :line 105,
   :var-type "function",
   :arglists ([throwable]),
   :doc
   "Returns CompilerExceptions in tact, but only the root cause of other\nthrowables",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/repl-exception"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "repl-prompt",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L41",
   :line 41,
   :var-type "function",
   :arglists ([]),
   :doc "Default :prompt hook for repl",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/repl-prompt"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "repl-read",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L78",
   :line 78,
   :var-type "function",
   :arglists ([request-prompt request-exit]),
   :doc
   "Default :read hook for repl. Reads from *in* which must either be an\ninstance of LineNumberingPushbackReader or duplicate its behavior of both\nsupporting .unread and collapsing all of CR, LF, and CRLF into a single\n\\newline. repl-read:\n  - skips whitespace, then\n    - returns request-prompt on start of line, or\n    - returns request-exit on end of stream, or\n    - reads an object from the input stream, then\n      - skips the next input character if it's end of line, then\n      - returns the object.",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/repl-read"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "skip-if-eol",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L46",
   :line 46,
   :var-type "function",
   :arglists ([s]),
   :doc
   "If the next character on stream s is a newline, skips it, otherwise\nleaves the stream untouched. Returns :line-start, :stream-end, or :body\nto indicate the relative location of the next character on s. The stream\nmust either be an instance of LineNumberingPushbackReader or duplicate\nits behavior of both supporting .unread and collapsing all of CR, LF, and\nCRLF to a single \\newline.",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/skip-if-eol"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "skip-whitespace",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L60",
   :line 60,
   :var-type "function",
   :arglists ([s]),
   :doc
   "Skips whitespace characters on stream s. Returns :line-start, :stream-end,\nor :body to indicate the relative location of the next character on s.\nInterprets comma as whitespace and semicolon as comment to end of line.\nDoes not interpret #! as comment to end of line because only one\ncharacter of lookahead is available. The stream must either be an\ninstance of LineNumberingPushbackReader or duplicate its behavior of both\nsupporting .unread and collapsing all of CR, LF, and CRLF to a single\n\\newline.",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/skip-whitespace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj",
   :name "with-bindings",
   :file "src/clj/clojure/main.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/2cc710e7aeaab08e0739debe21e2cc6b7020e1b1/src/clj/clojure/main.clj#L20",
   :line 20,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "Executes body in the context of thread-local bindings for several vars\nthat often need to be set!: *ns* *warn-on-reflection* *math-context*\n*print-meta* *print-length* *print-level* *compile-path*\n*command-line-args* *1 *2 *3 *e",
   :namespace "clojure.main",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.main-api.html#clojure.main/with-bindings"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "difference",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L46",
   :line 46,
   :var-type "function",
   :arglists ([s1] [s1 s2] [s1 s2 & sets]),
   :doc
   "Return a set that is the first set without elements of the remaining sets",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/difference"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "index",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L87",
   :line 87,
   :var-type "function",
   :arglists ([xrel ks]),
   :doc
   "Returns a map of the distinct values of ks in the xrel mapped to a\nset of the maps in xrel with the corresponding values of ks.",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/index"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "intersection",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L31",
   :line 31,
   :var-type "function",
   :arglists ([s1] [s1 s2] [s1 s2 & sets]),
   :doc "Return a set that is the intersection of the input sets",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/intersection"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "join",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L101",
   :line 101,
   :var-type "function",
   :arglists ([xrel yrel] [xrel yrel km]),
   :doc
   "When passed 2 rels, returns the rel corresponding to the natural\njoin. When passed an additional keymap, joins on the corresponding\nkeys.",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/join"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "map-invert",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L97",
   :line 97,
   :var-type "function",
   :arglists ([m]),
   :doc "Returns the map with the vals mapped to the keys.",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/map-invert"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "project",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L67",
   :line 67,
   :var-type "function",
   :arglists ([xrel ks]),
   :doc
   "Returns a rel of the elements of xrel with only the keys in ks",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/project"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "rename",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L82",
   :line 82,
   :var-type "function",
   :arglists ([xrel kmap]),
   :doc
   "Returns a rel of the maps in xrel with the keys in kmap renamed to the vals in kmap",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/rename"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "rename-keys",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L72",
   :line 72,
   :var-type "function",
   :arglists ([map kmap]),
   :doc
   "Returns the map with the keys in kmap renamed to the vals in kmap",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/rename-keys"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "select",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L61",
   :line 61,
   :var-type "function",
   :arglists ([pred xset]),
   :doc "Returns a set of the elements for which pred is true",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/select"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj",
   :name "union",
   :file "src/clj/clojure/set.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/set.clj#L19",
   :line 19,
   :var-type "function",
   :arglists ([] [s1] [s1 s2] [s1 s2 & sets]),
   :doc "Return a set that is the union of the input sets",
   :namespace "clojure.set",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.set-api.html#clojure.set/union"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj",
   :name "e",
   :file "src/clj/clojure/stacktrace.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj#L69",
   :line 69,
   :var-type "function",
   :arglists ([]),
   :doc
   "REPL utility.  Prints a brief stack trace for the root cause of the\nmost recent exception.",
   :namespace "clojure.stacktrace",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.stacktrace-api.html#clojure.stacktrace/e"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj",
   :name "print-cause-trace",
   :file "src/clj/clojure/stacktrace.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj#L60",
   :line 60,
   :var-type "function",
   :arglists ([tr] [tr n]),
   :doc
   "Like print-stack-trace but prints chained exceptions (causes).",
   :namespace "clojure.stacktrace",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.stacktrace-api.html#clojure.stacktrace/print-cause-trace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj",
   :name "print-stack-trace",
   :file "src/clj/clojure/stacktrace.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj#L41",
   :line 41,
   :var-type "function",
   :arglists ([tr] [tr n]),
   :doc
   "Prints a Clojure-oriented stack trace of tr, a Throwable.\nPrints a maximum of n stack frames (default: unlimited).\nDoes not print chained exceptions (causes).",
   :namespace "clojure.stacktrace",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.stacktrace-api.html#clojure.stacktrace/print-stack-trace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj",
   :name "print-throwable",
   :file "src/clj/clojure/stacktrace.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj#L36",
   :line 36,
   :var-type "function",
   :arglists ([tr]),
   :doc "Prints the class and message of a Throwable.",
   :namespace "clojure.stacktrace",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.stacktrace-api.html#clojure.stacktrace/print-throwable"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj",
   :name "print-trace-element",
   :file "src/clj/clojure/stacktrace.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj#L25",
   :line 25,
   :var-type "function",
   :arglists ([e]),
   :doc
   "Prints a Clojure-oriented view of one element in a stack trace.",
   :namespace "clojure.stacktrace",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.stacktrace-api.html#clojure.stacktrace/print-trace-element"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj",
   :name "root-cause",
   :file "src/clj/clojure/stacktrace.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/stacktrace.clj#L18",
   :line 18,
   :var-type "function",
   :arglists ([tr]),
   :doc "Returns the last 'cause' Throwable in a chain of Throwables.",
   :namespace "clojure.stacktrace",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.stacktrace-api.html#clojure.stacktrace/root-cause"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/template.clj",
   :name "apply-template",
   :file "src/clj/clojure/template.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/template.clj#L30",
   :line 30,
   :var-type "function",
   :arglists ([argv expr values]),
   :doc
   "For use in macros.  argv is an argument list, as in defn.  expr is\na quoted expression using the symbols in argv.  values is a sequence\nof values to be used for the arguments.\n\napply-template will recursively replace argument symbols in expr\nwith their corresponding values, returning a modified expr.\n\nExample: (apply-template '[x] '(+ x x) '[2])\n         ;=> (+ 2 2)",
   :namespace "clojure.template",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.template-api.html#clojure.template/apply-template"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/template.clj",
   :name "do-template",
   :file "src/clj/clojure/template.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/template.clj#L45",
   :line 45,
   :var-type "macro",
   :arglists ([argv expr & values]),
   :doc
   "Repeatedly copies expr (in a do block) for each group of arguments\nin values.  values are automatically partitioned by the number of\narguments in argv, an argument vector as in defn.\n\nExample: (macroexpand '(do-template [x y] (+ y x) 2 4 3 5))\n         ;=> (do (+ 4 2) (+ 5 3))",
   :namespace "clojure.template",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.template-api.html#clojure.template/do-template"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "*load-tests*",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L239",
   :line 239,
   :var-type "var",
   :arglists nil,
   :doc
   "True by default.  If set to false, no test functions will\nbe created by deftest, set-test, or with-test.  Use this to omit\ntests when compiling or loading production code.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/*load-tests*"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "*stack-trace-depth*",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L245",
   :line 245,
   :var-type "var",
   :arglists nil,
   :doc
   "The maximum depth of stack traces to print when an Exception\nis thrown during a test.  Defaults to nil, which means print the \ncomplete stack trace.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/*stack-trace-depth*"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "are",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L518",
   :line 518,
   :var-type "macro",
   :arglists ([argv expr & args]),
   :doc
   "Checks multiple assertions with a template expression.\nSee clojure.template/do-template for an explanation of\ntemplates.\n\nExample: (are [x y] (= x y)  \n              2 (+ 1 1)\n              4 (* 2 2))\nExpands to: \n         (do (is (= 2 (+ 1 1)))\n             (is (= 4 (* 2 2))))\n\nNote: This breaks some reporting features, such as line numbers.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/are"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "assert-any",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L404",
   :line 404,
   :var-type "function",
   :arglists ([msg form]),
   :doc
   "Returns generic assertion code for any test, including macros, Java\nmethod calls, or isolated symbols.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/assert-any"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "assert-predicate",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L386",
   :line 386,
   :var-type "function",
   :arglists ([msg form]),
   :doc
   "Returns generic assertion code for any functional predicate.  The\n'expected' argument to 'report' will contains the original form, the\n'actual' argument will contain the form with all its sub-forms\nevaluated.  If the predicate returns false, the 'actual' form will\nbe wrapped in (not...).",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/assert-predicate"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "compose-fixtures",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L618",
   :line 618,
   :var-type "function",
   :arglists ([f1 f2]),
   :doc
   "Composes two fixture functions, creating a new fixture function\nthat combines their behavior.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/compose-fixtures"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "deftest",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L557",
   :line 557,
   :var-type "macro",
   :arglists ([name & body]),
   :doc
   "Defines a test function with no arguments.  Test functions may call\nother tests, so tests may be composed.  If you compose tests, you\nshould also define a function named test-ns-hook; run-tests will\ncall test-ns-hook instead of testing all vars.\n\nNote: Actually, the test body goes in the :test metadata on the var,\nand the real function (the value of the var) calls test-var on\nitself.\n\nWhen *load-tests* is false, deftest is ignored.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/deftest"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "deftest-",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L573",
   :line 573,
   :var-type "macro",
   :arglists ([name & body]),
   :doc "Like deftest but creates a private var.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/deftest-"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "file-position",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L275",
   :line 275,
   :var-type "function",
   :arglists ([n]),
   :doc
   "Returns a vector [filename line-number] for the nth call up the\nstack.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/file-position"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "function?",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L375",
   :line 375,
   :var-type "function",
   :arglists ([x]),
   :doc
   "Returns true if argument is a function or a symbol that resolves to\na function (not a macro).",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/function?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "get-possibly-unbound-var",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L368",
   :line 368,
   :var-type "function",
   :arglists ([v]),
   :doc "Like var-get but returns nil if the var is unbound.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/get-possibly-unbound-var"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "inc-report-counter",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L300",
   :line 300,
   :var-type "function",
   :arglists ([name]),
   :doc
   "Increments the named counter in *report-counters*, a ref to a map.\nDoes nothing if *report-counters* is nil.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/inc-report-counter"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "is",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L501",
   :line 501,
   :var-type "macro",
   :arglists ([form] [form msg]),
   :doc
   "Generic assertion macro.  'form' is any predicate test.\n'msg' is an optional message to attach to the assertion.\n\nExample: (is (= 4 (+ 2 2)) \"Two plus two should be 4\")\n\nSpecial forms:\n\n(is (thrown? c body)) checks that an instance of c is thrown from\nbody, fails if not; then returns the thing thrown.\n\n(is (thrown-with-msg? c re body)) checks that an instance of c is\nthrown AND that the message on the exception matches (with\nre-matches) the regular expression re.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/is"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "join-fixtures",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L624",
   :line 624,
   :var-type "function",
   :arglists ([fixtures]),
   :doc
   "Composes a collection of fixtures, in order.  Always returns a valid\nfixture function, even if the collection is empty.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/join-fixtures"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "report",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L312",
   :line 312,
   :var-type "multimethod",
   :arglists nil,
   :doc
   "Generic reporting function, may be overridden to plug in\ndifferent report formats (e.g., TAP, JUnit).  Assertions such as\n'is' call 'report' to indicate results.  The argument given to\n'report' will be a map with a :type key.  See the documentation at\nthe top of test_is.clj for more information on the types of\narguments for 'report'.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/report"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "run-all-tests",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L695",
   :line 695,
   :var-type "function",
   :arglists ([] [re]),
   :doc
   "Runs all tests in all namespaces; prints results.\nOptional argument is a regular expression; only namespaces with\nnames matching the regular expression (with re-matches) will be\ntested.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/run-all-tests"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "run-tests",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L684",
   :line 684,
   :var-type "function",
   :arglists ([] [& namespaces]),
   :doc
   "Runs all tests in the given namespaces; prints results.\nDefaults to current namespace if none given.  Returns a map\nsummarizing test results.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/run-tests"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "set-test",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L581",
   :line 581,
   :var-type "macro",
   :arglists ([name & body]),
   :doc
   "Experimental.\nSets :test metadata of the named var to a fn with the given body.\nThe var must already exist.  Does not modify the value of the var.\n\nWhen *load-tests* is false, set-test is ignored.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/set-test"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "successful?",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L703",
   :line 703,
   :var-type "function",
   :arglists ([summary]),
   :doc
   "Returns true if the given test summary indicates all tests\nwere successful, false otherwise.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/successful?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "test-all-vars",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L649",
   :line 649,
   :var-type "function",
   :arglists ([ns]),
   :doc
   "Calls test-var on every var interned in the namespace, with fixtures.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/test-all-vars"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "test-ns",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L660",
   :line 660,
   :var-type "function",
   :arglists ([ns]),
   :doc
   "If the namespace defines a function named test-ns-hook, calls that.\nOtherwise, calls test-all-vars on the namespace.  'ns' is a\nnamespace object or a symbol.\n\nInternally binds *report-counters* to a ref initialized to\n*inital-report-counters*.  Returns the final, dereferenced state of\n*report-counters*.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/test-ns"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "test-var",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L635",
   :line 635,
   :var-type "function",
   :arglists ([v]),
   :doc
   "If v has a function in its :test metadata, calls that function,\nwith *testing-vars* bound to (conj *testing-vars* v).",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/test-var"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "testing",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L534",
   :line 534,
   :var-type "macro",
   :arglists ([string & body]),
   :doc
   "Adds a new string to the list of testing contexts.  May be nested,\nbut must occur inside a test function (deftest).",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/testing"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "testing-contexts-str",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L294",
   :line 294,
   :var-type "function",
   :arglists ([]),
   :doc
   "Returns a string representation of the current test context. Joins\nstrings in *testing-contexts* with spaces.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/testing-contexts-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "testing-vars-str",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L282",
   :line 282,
   :var-type "function",
   :arglists ([]),
   :doc
   "Returns a string representation of the current test.  Renders names\nin *testing-vars* as a list, then the source file and line of\ncurrent assertion.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/testing-vars-str"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "try-expr",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L486",
   :line 486,
   :var-type "macro",
   :arglists ([msg form]),
   :doc
   "Used by the 'is' macro to catch unexpected exceptions.\nYou don't call this.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/try-expr"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "use-fixtures",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L601",
   :line 601,
   :var-type "multimethod",
   :arglists nil,
   :doc
   "Wrap test runs in a fixture function to perform setup and\nteardown. Using a fixture-type of :each wraps every test\nindividually, while:once wraps the whole run in a single function.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/use-fixtures"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "with-test",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L545",
   :line 545,
   :var-type "macro",
   :arglists ([definition & body]),
   :doc
   "Takes any definition form (that returns a Var) as the first argument.\nRemaining body goes in the :test metadata function for that Var.\n\nWhen *load-tests* is false, only evaluates the definition, ignoring\nthe tests.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/with-test"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj",
   :name "with-test-out",
   :file "src/clj/clojure/test.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/607389029cfec50f32b73c00a6f66d0a1dbcda23/src/clj/clojure/test.clj#L265",
   :line 265,
   :var-type "macro",
   :arglists ([& body]),
   :doc "Runs body with *out* bound to the value of *test-out*.",
   :namespace "clojure.test",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test/with-test-out"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "keywordize-keys",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L90",
   :line 90,
   :var-type "function",
   :arglists ([m]),
   :doc
   "Recursively transforms all map keys from strings to keywords.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/keywordize-keys"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "macroexpand-all",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L118",
   :line 118,
   :var-type "function",
   :arglists ([form]),
   :doc "Recursively performs all possible macroexpansions in form.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/macroexpand-all"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "postwalk",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L52",
   :line 52,
   :var-type "function",
   :arglists ([f form]),
   :doc
   "Performs a depth-first, post-order traversal of form.  Calls f on\neach sub-form, uses f's return value in place of the original.\nRecognizes all Clojure data structures except sorted-map-by.\nConsumes seqs as with doall.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/postwalk"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "postwalk-demo",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L78",
   :line 78,
   :var-type "function",
   :arglists ([form]),
   :doc
   "Demonstrates the behavior of postwalk by printing each form as it is\nwalked.  Returns form.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/postwalk-demo"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "postwalk-replace",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L111",
   :line 111,
   :var-type "function",
   :arglists ([smap form]),
   :doc
   "Recursively transforms form by replacing keys in smap with their\nvalues.  Like clojure/replace but works on any data structure.  Does\nreplacement at the leaves of the tree first.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/postwalk-replace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "prewalk",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L60",
   :line 60,
   :var-type "function",
   :arglists ([f form]),
   :doc "Like postwalk, but does pre-order traversal.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/prewalk"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "prewalk-demo",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L84",
   :line 84,
   :var-type "function",
   :arglists ([form]),
   :doc
   "Demonstrates the behavior of prewalk by printing each form as it is\nwalked.  Returns form.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/prewalk-demo"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "prewalk-replace",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L104",
   :line 104,
   :var-type "function",
   :arglists ([smap form]),
   :doc
   "Recursively transforms form by replacing keys in smap with their\nvalues.  Like clojure/replace but works on any data structure.  Does\nreplacement at the root of the tree first.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/prewalk-replace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "stringify-keys",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L97",
   :line 97,
   :var-type "function",
   :arglists ([m]),
   :doc
   "Recursively transforms all map keys from keywords to strings.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/stringify-keys"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj",
   :name "walk",
   :file "src/clj/clojure/walk.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/walk.clj#L35",
   :line 35,
   :var-type "function",
   :arglists ([inner outer form]),
   :doc
   "Traverses form, an arbitrary data structure.  inner and outer are\nfunctions.  Applies inner to each element of form, building up a\ndata structure of the same type, then applies outer to the result.\nRecognizes all Clojure data structures except sorted-map-by.\nConsumes seqs as with doall.",
   :namespace "clojure.walk",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.walk-api.html#clojure.walk/walk"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/xml.clj",
   :name "parse",
   :file "src/clj/clojure/xml.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/xml.clj#L78",
   :line 78,
   :var-type "function",
   :arglists ([s] [s startparse]),
   :doc
   "Parses and loads the source s, which can be a File, InputStream or\nString naming a URI. Returns a tree of the xml/element struct-map,\nwhich has the keys :tag, :attrs, and :content. and accessor fns tag,\nattrs, and content. Other parsers can be supplied by passing\nstartparse, a fn taking a source and a ContentHandler and returning\na parser",
   :namespace "clojure.xml",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.xml-api.html#clojure.xml/parse"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "append-child",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L200",
   :line 200,
   :var-type "function",
   :arglists ([loc item]),
   :doc
   "Inserts the item as the rightmost child of the node at this loc,\nwithout moving",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/append-child"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "branch?",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L64",
   :line 64,
   :var-type "function",
   :arglists ([loc]),
   :doc "Returns true if the node at loc is a branch",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/branch?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "children",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L69",
   :line 69,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Returns a seq of the children of node at loc, which must be a branch",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/children"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "down",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L98",
   :line 98,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Returns the loc of the leftmost child of the node at this loc, or\nnil if no children",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/down"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "edit",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L189",
   :line 189,
   :var-type "function",
   :arglists ([loc f & args]),
   :doc
   "Replaces the node at this loc with the value of (f node args)",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/edit"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "end?",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L232",
   :line 232,
   :var-type "function",
   :arglists ([loc]),
   :doc "Returns true if loc represents the end of a depth-first walk",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/end?"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "insert-child",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L194",
   :line 194,
   :var-type "function",
   :arglists ([loc item]),
   :doc
   "Inserts the item as the leftmost child of the node at this loc,\nwithout moving",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/insert-child"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "insert-left",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L165",
   :line 165,
   :var-type "function",
   :arglists ([loc item]),
   :doc
   "Inserts the item as the left sibling of the node at this loc,\nwithout moving",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/insert-left"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "insert-right",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L174",
   :line 174,
   :var-type "function",
   :arglists ([loc item]),
   :doc
   "Inserts the item as the right sibling of the node at this loc,\nwithout moving",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/insert-right"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "left",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L150",
   :line 150,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Returns the loc of the left sibling of the node at this loc, or nil",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/left"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "leftmost",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L157",
   :line 157,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Returns the loc of the leftmost sibling of the node at this loc, or self",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/leftmost"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "lefts",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L87",
   :line 87,
   :var-type "function",
   :arglists ([loc]),
   :doc "Returns a seq of the left siblings of this loc",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/lefts"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "make-node",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L76",
   :line 76,
   :var-type "function",
   :arglists ([loc node children]),
   :doc
   "Returns a new branch node, given an existing node and new\nchildren. The loc is only used to supply the constructor.",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/make-node"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "next",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L206",
   :line 206,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Moves to the next loc in the hierarchy, depth-first. When reaching\nthe end, returns a distinguished loc detectable via end?. If already\nat the end, stays there.",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/next"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "node",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L60",
   :line 60,
   :var-type "function",
   :arglists ([loc]),
   :doc "Returns the node at loc",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/node"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "path",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L82",
   :line 82,
   :var-type "function",
   :arglists ([loc]),
   :doc "Returns a seq of nodes leading to this loc",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/path"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "prev",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L221",
   :line 221,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Moves to the previous loc in the hierarchy, depth-first. If already\nat the root, returns nil.",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/prev"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "remove",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L237",
   :line 237,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Removes the node at loc, returning the loc that would have preceded\nit in a depth-first walk.",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/remove"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "replace",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L183",
   :line 183,
   :var-type "function",
   :arglists ([loc node]),
   :doc "Replaces the node at this loc, without moving",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/replace"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "right",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L135",
   :line 135,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Returns the loc of the right sibling of the node at this loc, or nil",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/right"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "rightmost",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L142",
   :line 142,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Returns the loc of the rightmost sibling of the node at this loc, or self",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/rightmost"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "rights",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L92",
   :line 92,
   :var-type "function",
   :arglists ([loc]),
   :doc "Returns a seq of the right siblings of this loc",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/rights"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "root",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L124",
   :line 124,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "zips all the way up and returns the root node, reflecting any\nchanges.",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/root"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "seq-zip",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L34",
   :line 34,
   :var-type "function",
   :arglists ([root]),
   :doc "Returns a zipper for nested sequences, given a root sequence",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/seq-zip"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "up",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L111",
   :line 111,
   :var-type "function",
   :arglists ([loc]),
   :doc
   "Returns the loc of the parent of the node at this loc, or nil if at\nthe top",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/up"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "vector-zip",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L42",
   :line 42,
   :var-type "function",
   :arglists ([root]),
   :doc "Returns a zipper for nested vectors, given a root vector",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/vector-zip"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "xml-zip",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L50",
   :line 50,
   :var-type "function",
   :arglists ([root]),
   :doc
   "Returns a zipper for xml elements (as from xml/parse),\ngiven a root element",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/xml-zip"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj",
   :name "zipper",
   :file "src/clj/clojure/zip.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/zip.clj#L18",
   :line 18,
   :var-type "function",
   :arglists ([branch? children make-node root]),
   :doc
   "Creates a new zipper structure. \n\nbranch? is a fn that, given a node, returns true if can have\nchildren, even if it currently doesn't.\n\nchildren is a fn that, given a branch node, returns a seq of its\nchildren.\n\nmake-node is a fn that, given an existing node and a seq of\nchildren, returns a new branch node with the supplied children.\nroot is the root node.",
   :namespace "clojure.zip",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.zip-api.html#clojure.zip/zipper"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/junit.clj",
   :name "with-junit-output",
   :file "src/clj/clojure/test/junit.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/junit.clj#L182",
   :line 182,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "Execute body with modified test-is reporting functions that write\nJUnit-compatible XML output.",
   :namespace "clojure.test.junit",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test.junit/with-junit-output"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj",
   :name "print-tap-diagnostic",
   :file "src/clj/clojure/test/tap.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj#L50",
   :line 50,
   :var-type "function",
   :arglists ([data]),
   :doc
   "Prints a TAP diagnostic line.  data is a (possibly multi-line)\nstring.",
   :namespace "clojure.test.tap",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test.tap/print-tap-diagnostic"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj",
   :name "print-tap-fail",
   :file "src/clj/clojure/test/tap.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj#L62",
   :line 62,
   :var-type "function",
   :arglists ([msg]),
   :doc
   "Prints a TAP 'not ok' line.  msg is a string, with no line breaks",
   :namespace "clojure.test.tap",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test.tap/print-tap-fail"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj",
   :name "print-tap-pass",
   :file "src/clj/clojure/test/tap.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj#L57",
   :line 57,
   :var-type "function",
   :arglists ([msg]),
   :doc
   "Prints a TAP 'ok' line.  msg is a string, with no line breaks",
   :namespace "clojure.test.tap",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test.tap/print-tap-pass"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj",
   :name "print-tap-plan",
   :file "src/clj/clojure/test/tap.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj#L45",
   :line 45,
   :var-type "function",
   :arglists ([n]),
   :doc
   "Prints a TAP plan line like '1..n'.  n is the number of tests",
   :namespace "clojure.test.tap",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test.tap/print-tap-plan"}
  {:raw-source-url
   "https://github.com/clojure/clojure/raw/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj",
   :name "with-tap-output",
   :file "src/clj/clojure/test/tap.clj",
   :source-url
   "https://github.com/clojure/clojure/blob/76e7c4317dc3eac80c4908ac5e5fb885e302b2a4/src/clj/clojure/test/tap.clj#L106",
   :line 106,
   :var-type "macro",
   :arglists ([& body]),
   :doc
   "Execute body with modified test reporting functions that produce\nTAP output",
   :namespace "clojure.test.tap",
   :wiki-url
   "http://clojure.github.io/clojure//clojure.test-api.html#clojure.test.tap/with-tap-output"})}