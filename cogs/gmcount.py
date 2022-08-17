from discord.ext import commands


class GmCount(commands.Cog):
    def __init__(self, client) -> None:
        self.client = client

    # TODO: function to check cooldown of a member
    async def check_cooldown(self, member):
        """

        :param member:
        :return:
        """
        return

    # TODO: function to increment gn score of a member
        # - Make a gn function so that it counts how many times the user said GN and keep track of their score

    async def increment_score(self, member):
        """

        :param member:
        :return:
        """
        return

    # need to use "pass_context" so that function accepts context as param
    @commands.command(pass_context=True)
    async def gn(self, ctx):
        # Step 3: make a cooldown for the number of times a user can say gn
        # Step 1: increment gn counter of the user who invoked the command
        print("gn invoked")
        await ctx.message.send('Gn!')
        # Step 2: output gn score of that user who invoked the command in msg


def setup(client):
    client.add_cog(GmCount(client))



