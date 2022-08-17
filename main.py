import os
import discord
from dotenv import load_dotenv
load_dotenv()
from discord.ext import commands

# Initiate bot

client = commands.Bot(command_prefix='!', help_command=None)


# Checking to see if bot is online in the terminal
@client.event
async def on_ready():
    print("Good Evening, i am alive!")


# test to see if bot sends message
@client.command(pass_context=True)
async def on_message(ctx):
    msg = ctx.message.content
    if ctx.message.author == client.user:
        return
    if ctx.message.startswith("googoodnight"):
        await msg.channel.send("gn invoked using test command")

# Load cogs
initial_extensions = []

for filename in os.listdir('./cogs'):
    if filename.endswith('.py'):
        client.load_extension(f'cogs.{filename[:-3]}')

client.run(os.getenv('TOKEN'))
