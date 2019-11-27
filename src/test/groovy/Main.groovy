import spock.lang.*

// See: http://spockframework.org/spock/docs/1.3/all_in_one.html
class Main extends Specification {
	// Each test method gets its own copy of instance fields
	int x = 0
	int y = 0
	BigInteger big = Mock()

	// Runs once, before the first test method
	def setupSpec() {
	}

	// Runs before every test method
	def setup() {
	}

	// Runs after every test method
	def cleanup() {
	}

	// Runs once, after the last test method
	def cleanupSpec() {
	}

	def "fresh instance fields"(int i) {
		expect:
		x++ == 0
		y++ == 0

		where:
		i << (1..3) // Repeat test n times
	}

	def "data table"(a, b) {
		expect:
		a == b

		// Run test with different inputs using a data table
		where:
		a | b
		1 | 1
		2 | 2
	}
	
	def "mock test"() {
		given:
		// Stub the toString method so it returns a certain value.
		def expected = "I'm a BigInteger"
		big.toString() >> expected

		when:
		def s = big.toString()

		then:
		s == expected
	}
}
