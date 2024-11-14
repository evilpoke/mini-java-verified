package codegen;


import java.util.Arrays;

public class Declaration {
    private Parameter[] params;
    private Type typeofallparameters;


    public Type getTypeofallparameters() {
        return typeofallparameters;
    }

 /* public String[] getNames() {
   // return new String[]{Arrays.stream(params).map(s -> s.getName()).toString()};  //TODO: works??

    String[] rstring = new String[params.length];
    for(int i = 0; i < params.length; i++){
      rstring[i] = params[i].getName();
    }
    return  rstring;

  }*/

    public Parameter[] getParams() {
        return params;
    }

    public Declaration(String[] names) {
        super();
        typeofallparameters = Type.Int;
        params = new Parameter[names.length];
        for (int i = 0; i < params.length; i++) {
            params[i] = new Parameter(Type.Int, names[i]);

        }
    }

    public Declaration(Type type, String[] names) {
        super();
        typeofallparameters = type;
        params = new Parameter[names.length];
        for (int i = 0; i < params.length; i++) {
            params[i] = new Parameter(Type.Int, names[i]);

        }
    }


    public Declaration(Type type, String name) {
        super();
        if (type == Type.Int) {
            params = new Parameter[1];
            params[0] = new Parameter(Type.Int, name);
        } else if (type == Type.IntArray) {
            params = new Parameter[1];
            params[0] = new Parameter(Type.IntArray, name);
        }
        typeofallparameters = type;

    }


    public Declaration(String a) {
        super();
        params = new Parameter[1];
        params[0] = new Parameter(Type.Int, a);
        typeofallparameters = Type.Int;
    }

    public Declaration(String a, String b) {
        super();
        params = new Parameter[2];
        params[0] = new Parameter(Type.Int, a);
        params[1] = new Parameter(Type.Int, b);
        typeofallparameters = Type.Int;
    }

    public void accept(ProgramVisitor visitor) {
        visitor.visit(this);
    }
}
