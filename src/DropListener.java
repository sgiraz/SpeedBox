import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

class DropListener implements DropTargetListener
{

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
		                        System.out.println("Drop: " + ((File)file).getPath());
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