package mezz.jei.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;

import mezz.jei.config.Constants;

/**
 * Workaround for GuiScreen.drawHoveringText being protected instead of public.
 * (the method with FontRenderer is added by Forge and can't be AT'd).
 */
public class TooltipRenderer {
	private static final TooltipGuiScreen tooltipScreen = new TooltipGuiScreen();

	public static void drawHoveringText(Minecraft minecraft, String textLine, int x, int y) {
		@SuppressWarnings("unchecked")
		List<String> textLines = minecraft.fontRendererObj.listFormattedStringToWidth(textLine, Constants.MAX_TOOLTIP_WIDTH);
		drawHoveringText(minecraft, textLines, x, y, minecraft.fontRendererObj);
	}

	public static void drawHoveringText(Minecraft minecraft, List<String> textLines, int x, int y, FontRenderer font) {
		tooltipScreen.set(minecraft);
		tooltipScreen.drawHoveringText(textLines, x, y, font);

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
	}

	private static class TooltipGuiScreen extends GuiScreen {
		public void set(Minecraft minecraft) {
			this.mc = minecraft;
			this.itemRender = minecraft.getRenderItem();
			this.width = minecraft.currentScreen.width;
			this.height = minecraft.currentScreen.height;
		}

		@Override
		public void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
			super.drawHoveringText(textLines, x, y, font);
		}
	}
}
