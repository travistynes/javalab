package app.web.model;

class User {
	private String name;
	private int age;
	private int updates;

	public User(String name) {
		this.name = name;
	}

	public void update() {
		this.updates++;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getUpdates() {
		return this.updates;
	}

	public String toString() {
		return "name: " + this.name + ", age: " + this.age + ", updates: " + this.updates;
	}
}
