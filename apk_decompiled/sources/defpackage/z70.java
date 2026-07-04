package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z70 {
    private static final /* synthetic */ lr $ENTRIES;
    private static final /* synthetic */ z70[] $VALUES;
    public static final x70 Companion;
    public static final z70 ON_ANY;
    public static final z70 ON_CREATE;
    public static final z70 ON_DESTROY;
    public static final z70 ON_PAUSE;
    public static final z70 ON_RESUME;
    public static final z70 ON_START;
    public static final z70 ON_STOP;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [z70, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r0v2, types: [x70, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v1, types: [z70, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [z70, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [z70, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r5v1, types: [z70, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r7v1, types: [z70, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r9v1, types: [z70, java.lang.Enum] */
    static {
        ?? r0 = new Enum("ON_CREATE", 0);
        ON_CREATE = r0;
        ?? r1 = new Enum("ON_START", 1);
        ON_START = r1;
        ?? r3 = new Enum("ON_RESUME", 2);
        ON_RESUME = r3;
        ?? r5 = new Enum("ON_PAUSE", 3);
        ON_PAUSE = r5;
        ?? r7 = new Enum("ON_STOP", 4);
        ON_STOP = r7;
        ?? r9 = new Enum("ON_DESTROY", 5);
        ON_DESTROY = r9;
        ?? r11 = new Enum("ON_ANY", 6);
        ON_ANY = r11;
        z70[] z70VarArr = {r0, r1, r3, r5, r7, r9, r11};
        $VALUES = z70VarArr;
        $ENTRIES = new mr(z70VarArr);
        Companion = new Object();
    }

    public static z70 valueOf(String str) {
        return (z70) Enum.valueOf(z70.class, str);
    }

    public static z70[] values() {
        return (z70[]) $VALUES.clone();
    }

    public final a80 a() {
        switch (y70.a[ordinal()]) {
            case 1:
            case 2:
                return a80.g;
            case 3:
            case 4:
                return a80.h;
            case 5:
                return a80.i;
            case 6:
                return a80.e;
            case 7:
                throw new IllegalArgumentException(this + " has no target state");
            default:
                v7.k();
                return null;
        }
    }
}
