package com.sea.ftp.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.exception.io.FTPIOException;

/**
 * OPTS MLST
 * 
 * @author huachengwu
 *
 */
public class OPTS_MLST extends AbstractCommand {

    private static final String[] AVAILABLE_TYPES = {"Size", "Modify", "Type", "Perm"};

    private String[] validateSelectedTypes(final String types[]) {

        // 忽略空类型
        if (types == null) {
            return new String[0];
        }

        List<String> selectedTypes = new ArrayList<>();
        // 校验所有类型
        for (int i = 0; i < types.length; ++i) {
            for (int j = 0; j < AVAILABLE_TYPES.length; ++j) {
                if (AVAILABLE_TYPES[j].equalsIgnoreCase(types[i])) {
                    selectedTypes.add(AVAILABLE_TYPES[j]);
                    break;
                }
            }
        }

        return selectedTypes.toArray(new String[0]);
    }

    @Override
    protected void done(CommandContext context) throws FTPIOException {
        String[] args = context.getRequest().getArgs();

        String[] types;
        String listTypes;
        if (args.length == 1) {
            types = new String[0];
            listTypes = "";
        } else {
            listTypes = args[1];
            StringTokenizer st = new StringTokenizer(listTypes, ";");
            types = new String[st.countTokens()];
            for (int i = 0; i < types.length; ++i) {
                types[i] = st.nextToken();
            }
        }

        String[] validatedTypes = validateSelectedTypes(types);
        if (validatedTypes != null) {
            context.getFtpServerSession().setAtrribute("MLST.types", validatedTypes);
            write(context, FtpReply.REPLY_200_COMMAND_OKAY, "OPTS.MLST", listTypes);
        } else {
            write(context, FtpReply.REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS, "OPTS.MLST", listTypes);
        }
    }
}
