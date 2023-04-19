/**
 * create the render
 */
public class RendererFactory {
    public Renderer buildRenderer(String type, int size){
        switch (type) {
            case "console":
                return new ConsoleRenderer(size);
            case "none":
                return new VoidRenderer();
            default:
                return null;
        }
    }
}
