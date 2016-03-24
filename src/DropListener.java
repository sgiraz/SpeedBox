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

	private SendBox sendBox;

	public DropListener(SendBox sendBox)
	{
		this.sendBox = sendBox;
		
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
	}

	@Override
	public void dragEnter(DropTargetDragEvent event){}

	@Override
	public void dragExit(DropTargetEvent event){}

	@Override
	public void dragOver(DropTargetDragEvent event){}

	@Override
	public void dropActionChanged(DropTargetDragEvent event){}

}