package chromatix.ddui.element;

import chromatix.ddui.Observable;
import chromatix.ddui.element.options.LabelOptions;
import chromatix.ddui.properties.BooleanProperty;
import chromatix.ddui.properties.ObjectProperty;
import chromatix.ddui.properties.StringProperty;

public class LabelElement extends Element<String> {

    public LabelElement(String text, ObjectProperty parent) {
        this(text, LabelOptions.builder().build(), parent);
    }

    public LabelElement(String text, LabelOptions options, ObjectProperty parent) {
        super("label", parent);
        setText(text);
        applyVisibility(options);
    }

    public LabelElement(Observable<String> text, ObjectProperty parent) {
        this(text, LabelOptions.builder().build(), parent);
    }

    public LabelElement(Observable<String> text, LabelOptions options, ObjectProperty parent) {
        super("label", parent);
        setText(text);
        applyVisibility(options);
    }

    public String getText() {
        var prop = getProperty("text");
        if (prop instanceof StringProperty sp) return sp.getValue();
        return "";
    }

    public LabelElement setText(String text) {
        StringProperty property = resolveTextProperty();
        property.setValue(text);
        setProperty(property);
        return this;
    }

    public LabelElement setText(Observable<String> text) {
        StringProperty property = resolveTextProperty();
        property.setValue(text.getValue());

        text.subscribe(value -> {
            setText(value);
            return property;
        });

        setProperty(property);
        return this;
    }

    @Override
    public LabelElement setVisibility(boolean visible) {
        super.setVisibility(visible);
        setProperty(new BooleanProperty("label_visible", visible, this));
        return this;
    }

    @Override
    public LabelElement setVisibility(Observable<Boolean> visible) {
        super.setVisibility(visible);
        var property = new BooleanProperty("label_visible", visible.getValue(), this);
        visible.subscribe(value -> {
            setVisibility(value);
            return property;
        });
        setProperty(property);
        return this;
    }

    private StringProperty resolveTextProperty() {
        var existing = getProperty("text");
        return (existing instanceof StringProperty sp)
                ? sp
                : new StringProperty("text", "", this);
    }

    @SuppressWarnings("unchecked")
    private void applyVisibility(LabelOptions options) {
        if (options.getVisible() instanceof Observable<?> obs) {
            setVisibility((Observable<Boolean>) obs);
        } else {
            setVisibility((Boolean) options.getVisible());
        }
    }
}