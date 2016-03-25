import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

class DropListener implements DropTargetListener
{

	private SendBoxGUI sendBox;
	private Color defaultBackgroundColor;

	public DropListener(SendBoxGUI sendBox)
	{
		this.sendBox = sendBox;
		//defaultBackgroundColor = sendBox.getPane().getBackground();	
	}
	
	@Override
	public void drop(DropTargetDropEvent event)
	{
		event.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable transferable = event.getTransferable();

		// Get the data formats of the dropped item
		DataFlavor[] flavors = transferable.getTransferDataFlavors();

		for (DataFlavor flavor : flavors)
		{
			try
			{
				if (flavor.isFlavorJavaFileListType())
				{
					Object data = transferable.getTransferData(flavor);
					if(data instanceof List<?>)
					{
						List<?> files = (List<?>) data;
						for (Object file : files)
						{
							if(file instanceof File)
							{ 
								sendBox.drop((File)file);
							}
							else 
								System.out.println("This is not a file"); 
						}
					}
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		event.dropComplete(true);
		System.out.println("drop complete");
		//sendBox.getPane().setBackground(defaultBackgroundColor);
	}

	@Override
	public void dragEnter(DropTargetDragEvent event){
		//sendBox.getPane().setBackground(new Color(152, 251, 152));
	}

	@Override
	public void dragExit(DropTargetEvent event){
		//sendBox.getPane().setBackground(defaultBackgroundColor);
	}

	@Override
	public void dragOver(DropTargetDragEvent event){}

	@Override
	public void dropActionChanged(DropTargetDragEvent event){}

}