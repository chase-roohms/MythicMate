# MythicMate

A powerful D&D Discord bot designed for seamless remote gameplay. MythicMate provides intuitive dice rolling, comprehensive rules referencing, and AI-powered assistance—all optimized for speed and ease of use.

<img src="images/mythicmate-header.webp" alt="MythicMate Header" width="100%">

## Features

### Flexible Dice Rolling
- **Simple Syntax**: Accepts any dice notation with automatic parsing (e.g., `2d20 + 1d6 + 5`)
- **Forgiving Input**: Handles spaces and typos gracefully, rolling recognized dice while alerting you to errors
- **Detailed Results**: Shows individual die results for dramatic effect
- **Multiple Roll Types**:
  - `/roll` - Standard dice rolling
  - `/rolladv` - Roll with advantage
  - `/rolldisadv` - Roll with disadvantage
  - `/rollgwf` - Great Weapon Fighting (reroll 1s and 2s)
  - `/rollempower` - Sorcerer Empowered Spell (reroll dice below a threshold)

### Rules & Reference Database
- **Fast Lookups**: Lightning-fast autocomplete suggestions for spells, conditions, feats, and more
- **Offline Access**: All data stored locally for instant responses
- **Commands**:
  - `/spell` - Look up spell details
  - `/conditions` - View condition descriptions
  - `/feat` - Search feats
  - `/lineage` - Browse lineage options
  - `/class` - View class information
  - `/subclass` - Look up subclass details
  - `/damages` - Reference damage types
  - `/cover` - Cover rules

### AI Integration
- **ChatGPT Powered**: `/ask` command for complex rule questions
- **Contextual Answers**: Precise, pre-formatted prompts ensure relevant D&D responses
- **Multithreaded**: AI requests run on separate threads to keep the bot responsive

### Web Scraping & Updates
- **Auto-Update**: `/update` command scrapes D&D wikis for the latest content
- **Password Protected**: Secured to prevent unauthorized database updates
- **Pre-Formatted**: Scraped data stored in Discord Markdown format for instant display

## Quick Start with Docker Compose

### Prerequisites
- Docker and Docker Compose installed
- Discord Bot Token ([Create one here](https://discord.com/developers/applications))
- OpenAI API Key (for `/ask` command)
- A password for the `/update` command

### Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/chase-roohms/mythicmate.git
   cd mythicmate
   ```

2. **Create a `.env` file** in the project root:
   ```bash
   MYTHICMATE_TOKEN=your_discord_bot_token_here
   MYTHICMATE_PASSWORD=your_update_password_here
   MYTHICMATE_GPT_KEY=your_openai_api_key_here
   ```

3. **Start the bot**:
   ```bash
   docker-compose up -d
   ```

4. **View logs**:
   ```bash
   docker-compose logs -f
   ```

5. **Stop the bot**:
   ```bash
   docker-compose down
   ```

### Docker Compose Configuration

The [docker-compose.yml](docker-compose.yml) file is pre-configured with:
- **Automatic Restarts**: Bot restarts if it crashes
- **Persistent Database**: `./database` directory mounted for data persistence
- **Environment Variables**: Loaded from `.env` file
- **Security**: Runs as non-root user

### Building from Source

If you want to build the Docker image locally instead of using the pre-built image:

1. **Modify docker-compose.yml**:
   ```yaml
   services:
     mythicmate:
       build: .  # Instead of using image: neonvariant/mythicmate:main
       container_name: mythicmate-bot
       # ... rest of config
   ```

2. **Build and run**:
   ```bash
   docker-compose up -d --build
   ```

## Local Development Setup

### Prerequisites
- Java 21 (Eclipse Temurin recommended)
- Maven 3.8+

### Build & Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/chase-roohms/mythicmate.git
   cd mythicmate
   ```

2. **Set environment variables**:
   ```bash
   export MYTHICMATE_TOKEN=your_discord_bot_token
   export MYTHICMATE_PASSWORD=your_update_password
   export MYTHICMATE_GPT_KEY=your_openai_api_key
   ```

3. **Build the project**:
   ```bash
   mvn clean package
   ```

4. **Run the bot**:
   ```bash
   java -jar target/MythicMate-1.0-SNAPSHOT.jar
   ```

## Project Structure

```
mythicmate/
├── src/main/java/
│   ├── Bot/              # Main Discord bot entry point
│   ├── Events/           # Command manager and autocomplete
│   ├── ICommands/        # Slash command implementations
│   │   └── Roll/         # Dice rolling commands
│   ├── Functions/        # Utility functions
│   ├── Database/         # Database management
│   ├── Scrapers/         # Web scraping utilities
│   ├── ICommandsHelpers/ # Command helper classes
│   └── Authenticate/     # Environment variable handling
├── database/             # Persistent data (created at runtime)
├── Dockerfile            # Multi-stage Docker build
├── docker-compose.yml    # Docker Compose configuration
├── entrypoint.sh         # Container entrypoint script
└── pom.xml               # Maven configuration
```

## Dependencies

- **[JDA 5.0.0](https://github.com/DV8FromTheWorld/JDA)** - Java Discord API
- **[JSoup 1.16.2](https://jsoup.org/)** - HTML parsing for web scraping
- **[Apache Commons Lang 3.18.0](https://commons.apache.org/proper/commons-lang/)** - Utility functions
- **[dotenv-java 3.0.0](https://github.com/cdimascio/dotenv-java)** - Environment variable management
- **[SLF4J 2.0.9](https://www.slf4j.org/)** - Logging framework

## Security

- Bot runs as non-root user in Docker container
- Environment variables used for sensitive data (tokens, keys)
- `/update` command password-protected
- No exposed ports (Discord bot uses WebSocket)

## Available Commands

| Command | Description |
|---------|-------------|
| `/roll [dice]` | Roll dice with standard notation |
| `/rolladv [dice]` | Roll with advantage |
| `/rolldisadv [dice]` | Roll with disadvantage |
| `/rollgwf [dice]` | Roll with Great Weapon Fighting |
| `/rollempower [dice] [threshold]` | Roll with Empowered Spell |
| `/spell [name]` | Look up spell details |
| `/conditions [name]` | View condition information |
| `/feat [name]` | Search for feats |
| `/lineage [name]` | Browse lineage options |
| `/class [name]` | View class information |
| `/subclass [name]` | Look up subclass details |
| `/damages` | Reference damage types |
| `/cover` | View cover rules |
| `/ask [question]` | Ask ChatGPT a D&D question |
| `/update [password]` | Update database from web sources |
| `/commandlist` | List all available commands |

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the terms in the [LICENSE](LICENSE) file.

## Why MythicMate?

Remote D&D sessions deserve a bot that just *works*. No complex syntax, no paywalls, no account linking—just fast, intuitive commands that keep your game flowing. Whether you're rolling for initiative or looking up a spell mid-combat, MythicMate has your back.

---

Built with ☕ and ⚔️ by [chase-roohms](https://github.com/chase-roohms)

